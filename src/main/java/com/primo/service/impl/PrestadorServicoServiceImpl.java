package com.primo.service.impl;

import com.primo.dto.request.AvatarRequest;
import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.cadastro.PrestadorServico;
import com.primo.domain.cadastro.TipoServico;
import com.primo.domain.constant.Constantes;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.domain.enums.TipoPessoa;
import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.PrestadorRequest;
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
                throw new NotFoundException("Nenhum prestador de serviço encontrado!", this);
            }
            for (PrestadorServicoDTO prestadorServicoDTO : listaPrestadoresServico) {
                prestadorServicoDTO.setEndereco(enderecoService.buscarPeloCodigoPessoa(prestadorServicoDTO.getCodigo()));
            }
            return listaPrestadoresServico;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de buscar os prestadores de serviço! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os prestadores de serviço!", "Termo pesquisa: " + termoPesquisa, e.getMessage(), this);
        }
    }

    public PrestadorServicoDTO buscarPeloCodigo(Long codigoPessoa) {
        try {
            validationUtil.validarCampoVazio(codigoPessoa, "Código");
            var prestadorServicoDTO = prestadorServicoRepository.buscarPeloCodigo(codigoPessoa);
            if (prestadorServicoDTO == null) {
                throw new NotFoundException("Nenhum prestador de serviço encontrado pelo código!", this);
            }
            prestadorServicoDTO.setEndereco(enderecoService.buscarPeloCodigoPessoa(prestadorServicoDTO.getCodigo()));
            return prestadorServicoDTO;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de buscar o prestador de serviço! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o prestador de serviço!", "Código: " + codigoPessoa, e.getMessage(), this);
        }
    }

    @Transactional
    public void cadastrar(PrestadorRequest request) {
        try {
            validarCamposPrestador(request);
            final Pessoa pessoa = pessoaService.salvar(request.nome(), request.cnpj(), request.telefone(), request.email(), TipoPessoa.PRESTADOR);
            final String senha = new BCryptPasswordEncoder().encode(request.senha());
            usuarioService.salvar(pessoa.getCodigo(), request.email(), senha, PermissaoUsuario.USUARIO);
            enderecoService.salvar(pessoa.getCodigo(), request.endereco());
            salvarPrestadorServico(pessoa.getCodigo(), request.codigoTipoServico(), request.valorServico());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de cadastrar o prestador de serviço! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do prestador de serviço!", "Nome: " + request.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposPrestador(PrestadorRequest request) throws BadRequestException {
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
                    .codigoAvatar(Constantes.CODIGO_AVATAR_PADRAO)
                    .indicadorAtivo(Boolean.TRUE)
                    .build();
            prestadorServicoRepository.save(prestadorServico);
        } catch (NotFoundException e) {
            throw e;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de salvar o prestador de serviço! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o prestador de serviço com código de pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

    public void atualizarAvatar(Long codigoPessoa, AvatarRequest avatarRequest) {
        try {
            validarCamposAntesAtualizarAvatar(codigoPessoa, avatarRequest);
            prestadorServicoRepository.atualizarAvatar(codigoPessoa, avatarRequest.codigo());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de atualizar o avatar do prestador de serviço! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar o avatar do prestador de serviço!", "Código: " + codigoPessoa + " Código do avatar: " + avatarRequest.codigo(), e.getMessage(), this);
        }
    }

    private void validarCamposAntesAtualizarAvatar(Long codigoPessoa, AvatarRequest avatarRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(codigoPessoa, "Código do prestador de serviço");
        validationUtil.validarCampoVazio(avatarRequest, "Avatar");
        validationUtil.validarCampoVazio(avatarRequest.codigo(), "Código do avatar");
    }

    public void inativar(Long codigoPessoa) {
        try {
            validationUtil.validarCampoVazio(codigoPessoa, "Código do prestador de serviço");
            prestadorServicoRepository.inativar(codigoPessoa);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de inativar o prestador de serviço! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao inativar o prestador de serviço!", "Código: " + codigoPessoa, e.getMessage(), this);
        }
    }

}
