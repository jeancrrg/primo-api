package com.primo.service.impl;

import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.cadastro.PrestadorServico;
import com.primo.domain.cadastro.TipoServico;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.exception.NotFoundException;
import com.primo.repository.PrestadorServicoRepository;
import com.primo.service.*;
import com.primo.util.FormatterUtil;
import com.primo.util.ValidationUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PrestadorServicoServiceImpl implements PrestadorServicoService {

    private final PrestadorServicoRepository prestadorServicoRepository;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private final TipoServicoService tipoServicoService;
    private final ValidationUtil validationUtil;
    private final FormatterUtil formatterUtil;

    public PrestadorServicoServiceImpl(PrestadorServicoRepository prestadorServicoRepository,
                                       PessoaService pessoaService,
                                       UsuarioService usuarioService,
                                       EnderecoService enderecoService,
                                       TipoServicoService tipoServicoService,
                                       ValidationUtil validationUtil,
                                       FormatterUtil formatterUtil) {
        this.prestadorServicoRepository = prestadorServicoRepository;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
        this.enderecoService = enderecoService;
        this.tipoServicoService = tipoServicoService;
        this.validationUtil = validationUtil;
        this.formatterUtil = formatterUtil;
    }

    public List<PrestadorServicoDTO> buscar(String termoPesquisa) {
        try {
            if (!validationUtil.isEmptyTexto(termoPesquisa)) {
                termoPesquisa = formatterUtil.formatarTextoParaConsulta(termoPesquisa);
            }
            List<PrestadorServicoDTO> listaPrestadoresServico = prestadorServicoRepository.buscar(termoPesquisa);
            if (validationUtil.isEmptyList(listaPrestadoresServico)) {
                throw new NotFoundException("Falha ao buscar os prestadores de serviço! Nenhum prestador de serviço encontrado!", this);
            }
            for (PrestadorServicoDTO prestadorServicoDTO : listaPrestadoresServico) {
                prestadorServicoDTO.setEndereco(enderecoService.buscarPeloCodigoPessoa(prestadorServicoDTO.getCodigo()));
            }
            return listaPrestadoresServico;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os prestadores de serviço!", "Termo pesquisa: " + termoPesquisa, e.getMessage(), this);
        }
    }

    @Transactional
    public void cadastrar(CadastroPrestadorRequest request) {
        try {
            validarCamposPrestador(request);
            final Pessoa pessoa = pessoaService.salvar(request.nome(), request.cnpj(), request.telefone(), request.email());
            final String senha = new BCryptPasswordEncoder().encode(request.senha());
            usuarioService.salvar(pessoa.getCodigo(), request.email(), senha, PermissaoUsuario.USUARIO);
            enderecoService.salvar(pessoa.getCodigo(), request.endereco());
            salvarPrestadorServico(pessoa.getCodigo(), request.codigoTipoServico(), request.valorServico());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao cadastrar o prestador de serviço! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do prestador de serviço!", "Nome: " + request.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposPrestador(CadastroPrestadorRequest request) throws BadRequestException {
        validationUtil.validarCampoVazio(request, "Informações do prestador");
        validationUtil.validarCampoVazio(request.nome(), "Nome do prestador");
        validationUtil.validarCampoVazio(request.telefone(), "Telefone do prestador");
        validationUtil.validarCampoVazio(request.email(), "Email do prestador");
        validationUtil.validarCampoVazio(request.senha(), "Senha do prestador");
        validationUtil.validarCampoVazio(request.cnpj(), "Cnpj do prestador");
        validationUtil.validarCampoVazio(request.endereco(), "Endereço do prestador");
        validationUtil.validarCampoVazio(request.codigoTipoServico(), "Código do tipo do serviço do prestador");
        validationUtil.validarCampoVazio(request.valorServico(), "Valor do serviço do prestador");
        if (request.valorServico().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor do serviço não pode ser menor ou igual a zero!");
        }
    }

    private void salvarPrestadorServico(Long codigoPessoa, Integer codigoTipoServico, BigDecimal valorServico) throws BadRequestException, NotFoundException, InternalServerErrorException {
        try {
            final TipoServico tipoServico = tipoServicoService.buscarPeloCodigo(codigoTipoServico);
            if (tipoServico == null) {
                throw new NotFoundException("Falha ao salvar o prestador de serviço! Nenhum tipo de serviço encontrado com o código: " + codigoTipoServico, this);
            }
            final var prestadorServico = PrestadorServico.builder()
                    .codigoPessoa(codigoPessoa)
                    .tipoServico(tipoServico)
                    .valorServico(valorServico)
                    .build();
            prestadorServicoRepository.save(prestadorServico);
        } catch (NotFoundException e) {
            throw e;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao salvar o prestador de serviço! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o prestador de serviço com código de pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

}
