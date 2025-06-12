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
    public void cadastrar(ClienteRequest clienteRequest) {
        try {
            validarCamposCliente(clienteRequest);
            final String senhaCriptografada = new BCryptPasswordEncoder().encode(clienteRequest.senha());
            final Pessoa pessoa = pessoaService.salvar(clienteRequest.nome(), clienteRequest.cpf(), clienteRequest.telefone(), clienteRequest.email(), TipoPessoa.CLIENTE);
            usuarioService.salvar(pessoa.getCodigo(), clienteRequest.email(), senhaCriptografada, PermissaoUsuario.USUARIO);
            veiculoService.salvar(pessoa.getCodigo(), clienteRequest.modeloVeiculo(), clienteRequest.anoVeiculo());
            clienteRepository.save(new Cliente(pessoa.getCodigo(), Constantes.CODIGO_AVATAR_PADRAO, Boolean.TRUE));
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de cadastrar o cliente! - " + e.getMessage(), this);
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao cadastrar o cliente!", "CPF: " + clienteRequest.cpf(), e.getMessage(), this);
        }
    }

    private void validarCamposCliente(ClienteRequest clienteRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(clienteRequest, "Informações do cliente");
        validationUtil.validarCampoVazio(clienteRequest.nome(), "Nome do cliente");
        validationUtil.validarCampoVazio(clienteRequest.telefone(), "Telefone do cliente");
        validationUtil.validarCampoVazio(clienteRequest.cpf(), "CPF do cliente");
        validationUtil.validarCampoVazio(clienteRequest.email(), "Email do cliente");
        validationUtil.validarCampoVazio(clienteRequest.senha(), "Senha do cliente");
        validationUtil.validarCampoVazio(clienteRequest.modeloVeiculo(), "Modelo do veículo do cliente");
        validationUtil.validarCampoVazio(clienteRequest.anoVeiculo(), "Ano do veículo do cliente");
        if (clienteRequest.anoVeiculo() <= 1000) {
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

    public boolean verificarPossuiCadastroInativo(Long codigo) {
        try {
            validationUtil.validarCampoVazio(codigo, "Código do cliente");
            return clienteRepository.verificarPossuiCadastroInativo(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de verificar se possui o cadastro do cliente inativo! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao verificar se possui o cadastro do cliente inativo!", "Código: " + codigo, e.getMessage(), this);
        }
    }

}
