package com.primo.service.impl;

import com.primo.dto.request.AvatarRequest;
import com.primo.domain.cadastro.Cliente;
import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.constant.Constantes;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.domain.enums.TipoPessoa;
import com.primo.dto.request.ClienteRequest;
import com.primo.dto.response.ClienteResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.ConflictException;
import com.primo.exception.InternalServerErrorException;
import com.primo.exception.NotFoundException;
import com.primo.repository.ClienteRepository;
import com.primo.service.ClienteService;
import com.primo.service.PessoaService;
import com.primo.service.UsuarioService;
import com.primo.service.VeiculoService;
import com.primo.util.ValidationUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;
    private final VeiculoService veiculoService;
    private final ValidationUtil validationUtil;

    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              PessoaService pessoaService,
                              UsuarioService usuarioService,
                              VeiculoService veiculoService,
                              ValidationUtil validationUtil) {
        this.clienteRepository = clienteRepository;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
        this.veiculoService = veiculoService;
        this.validationUtil = validationUtil;
    }

    public ClienteResponse buscar(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código");
            ClienteResponse cliente = clienteRepository.buscar(codigo);
            if (cliente == null) {
                throw new NotFoundException("Nenhum cliente encontrado!", this);
            }
            return cliente;
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de buscar o cliente! - " + e.getMessage(), this);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o cliente!", "Código: " + codigo, e.getMessage(), this);
        }
    }

    @Transactional
    public void cadastrar(ClienteRequest request) {
        try {
            validarCamposCliente(request);
            final String senhaCriptografada = new BCryptPasswordEncoder().encode(request.senha());
            final Pessoa pessoa = pessoaService.salvar(request.nome(), request.cpf(), request.telefone(), request.email(), TipoPessoa.CLIENTE);
            usuarioService.salvar(pessoa.getCodigo(), request.email(), senhaCriptografada, PermissaoUsuario.USUARIO);
            veiculoService.salvar(pessoa.getCodigo(), request.modeloVeiculo(), request.anoVeiculo());
            clienteRepository.save(new Cliente(pessoa.getCodigo(), Constantes.CODIGO_AVATAR_PADRAO, Boolean.TRUE));
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de cadastrar o cliente! - " + e.getMessage(), this);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao cadastrar o cliente!", "Nome: " + request.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposCliente(ClienteRequest request) throws BadRequestException {
        validationUtil.validarCampoVazio(request, "Informações do cliente");
        validationUtil.validarCampoVazio(request.nome(), "Nome do cliente");
        validationUtil.validarCampoVazio(request.telefone(), "Telefone do cliente");
        validationUtil.validarCampoVazio(request.cpf(), "CPF do cliente");
        validationUtil.validarCampoVazio(request.email(), "Email do cliente");
        validationUtil.validarCampoVazio(request.senha(), "Senha do cliente");
        validationUtil.validarCampoVazio(request.modeloVeiculo(), "Modelo do veículo do cliente");
        validationUtil.validarCampoVazio(request.anoVeiculo(), "Ano do veículo do cliente");
        if (request.anoVeiculo() <= 1000) {
            throw new BadRequestException("Ano do veículo inválido!");
        }
    }

    public void atualizarAvatar(Long codigo, AvatarRequest avatarRequest) {
        try {
            validarCamposAntesAtualizarAvatar(codigo, avatarRequest);
            clienteRepository.atualizarAvatar(codigo, avatarRequest.codigo());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de atualizar o avatar do cliente! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar o avatar do cliente!", "Código: " + codigo + " Código do avatar: " + avatarRequest.codigo(), e.getMessage(), this);
        }
    }

    private void validarCamposAntesAtualizarAvatar(Long codigo, AvatarRequest avatarRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(codigo, "Código do cliente");
        validationUtil.validarCampoVazio(avatarRequest, "Avatar");
        validationUtil.validarCampoVazio(avatarRequest.codigo(), "Código do avatar");
    }

    public void inativar(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código do cliente");
            clienteRepository.inativar(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de inativar o cliente! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao inativar o cliente!", "Código: " + codigo, e.getMessage(), this);
        }
    }

}
