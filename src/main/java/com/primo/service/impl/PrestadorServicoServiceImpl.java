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

    public PrestadorServicoDTO buscarPeloCodigo(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código");
            var prestadorServicoDTO = prestadorServicoRepository.buscarPeloCodigo(codigo);
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
            throw new InternalServerErrorException("Erro ao buscar o prestador de serviço!", "Código: " + codigo, e.getMessage(), this);
        }
    }

    @Transactional
    public void cadastrar(PrestadorRequest prestadorRequest) {
        try {
            validarCamposPrestador(prestadorRequest);
            final Pessoa pessoa = pessoaService.salvar(prestadorRequest.nome(), prestadorRequest.cnpj(), prestadorRequest.telefone(), prestadorRequest.email(), TipoPessoa.PRESTADOR);
            final String senha = new BCryptPasswordEncoder().encode(prestadorRequest.senha());
            usuarioService.salvar(pessoa.getCodigo(), prestadorRequest.email(), senha, PermissaoUsuario.USUARIO);
            enderecoService.salvar(pessoa.getCodigo(), prestadorRequest.endereco());
            salvarPrestadorServico(pessoa.getCodigo(), prestadorRequest.codigoTipoServico(), prestadorRequest.valorServico());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de cadastrar o prestador de serviço! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do prestador de serviço!", "Nome: " + prestadorRequest.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposPrestador(PrestadorRequest prestadorRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(prestadorRequest, "Informações do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.nome(), "Nome do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.telefone(), "Telefone do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.email(), "Email do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.senha(), "Senha do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.cnpj(), "Cnpj do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.endereco(), "Endereço do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.codigoTipoServico(), "Código do tipo do serviço do prestador");
        validationUtil.validarCampoVazio(prestadorRequest.valorServico(), "Valor do serviço do prestador");
        if (prestadorRequest.valorServico().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("O valor do serviço não pode ser menor ou igual a zero!");
        }
    }

    private void salvarPrestadorServico(Long codigo, Integer codigoTipoServico, BigDecimal valorServico) throws BadRequestException, NotFoundException, InternalServerErrorException {
        try {
            final TipoServico tipoServico = tipoServicoService.buscarPeloCodigo(codigoTipoServico);
            if (tipoServico == null) {
                throw new NotFoundException("Falha ao salvar o prestador de serviço! Nenhum tipo de serviço encontrado com o código: " + codigoTipoServico, this);
            }
            final var prestadorServico = PrestadorServico.builder()
                    .codigo(codigo)
                    .tipoServico(tipoServico)
                    .valorServico(valorServico)
                    .codigoAvatar(Constantes.CODIGO_AVATAR_PADRAO)
                    .indicadorAtivo(Boolean.TRUE)
                    .indicadorOnline(Boolean.FALSE)
                    .build();
            prestadorServicoRepository.save(prestadorServico);
        } catch (NotFoundException e) {
            throw e;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de salvar o prestador de serviço! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o prestador de serviço: " + codigo + "! - " + e.getMessage());
        }
    }

    public void atualizarAvatar(Long codigo, AvatarRequest avatarRequest) {
        try {
            validarCamposAntesAtualizarAvatar(codigo, avatarRequest);
            prestadorServicoRepository.atualizarAvatar(codigo, avatarRequest.codigo());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de atualizar o avatar do prestador de serviço! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar o avatar do prestador de serviço!", "Código: " + codigo + " Código do avatar: " + avatarRequest.codigo(), e.getMessage(), this);
        }
    }

    private void validarCamposAntesAtualizarAvatar(Long codigo, AvatarRequest avatarRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(codigo, "Código do prestador de serviço");
        validationUtil.validarCampoVazio(avatarRequest, "Avatar");
        validationUtil.validarCampoVazio(avatarRequest.codigo(), "Código do avatar");
    }

    public void inativar(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código do prestador de serviço");
            prestadorServicoRepository.inativar(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de inativar o prestador de serviço! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao inativar o prestador de serviço!", "Código: " + codigo, e.getMessage(), this);
        }
    }

    public boolean verificarPossuiCadastroInativo(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código do prestador de serviço");
            return prestadorServicoRepository.verificarPossuiCadastroInativo(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de verificar se possui o cadastro do prestador de serviço inativo! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao verificar se possui o cadastro do prestador de serviço inativo!", "Código: " + codigo, e.getMessage(), this);
        }
    }

}
