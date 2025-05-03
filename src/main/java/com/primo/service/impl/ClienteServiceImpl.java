package com.primo.service.impl;

import com.primo.domain.cadastro.Cliente;
import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.dto.request.CadastroClienteRequest;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
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

    public ClienteServiceImpl(ClienteRepository clienteRepository, PessoaService pessoaService, UsuarioService usuarioService, VeiculoService veiculoService, ValidationUtil validationUtil) {
        this.clienteRepository = clienteRepository;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
        this.veiculoService = veiculoService;
        this.validationUtil = validationUtil;
    }

    @Transactional
    public void cadastrar(CadastroClienteRequest request) {
        try {
            validarCamposCliente(request);
            final Pessoa pessoa = pessoaService.salvar(request.nome(), null, request.telefone(), request.email());
            final String senhaCriptografada = new BCryptPasswordEncoder().encode(request.senha());
            usuarioService.salvar(pessoa.getCodigo(), request.email(), senhaCriptografada, PermissaoUsuario.USUARIO);
            veiculoService.salvar(pessoa.getCodigo(), request.modeloVeiculo(), request.anoVeiculo());
            clienteRepository.save(new Cliente(pessoa.getCodigo(), Boolean.TRUE));
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao cadastrar o cliente! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do cliente!", "Nome: " + request.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposCliente(CadastroClienteRequest request) throws BadRequestException {
        validationUtil.validarCampoVazio(request, "Informações do cliente");
        validationUtil.validarCampoVazio(request.nome(), "Nome do cliente");
        validationUtil.validarCampoVazio(request.telefone(), "Telefone do cliente");
        validationUtil.validarCampoVazio(request.email(), "Email do cliente");
        validationUtil.validarCampoVazio(request.senha(), "Senha do cliente");
        validationUtil.validarCampoVazio(request.modeloVeiculo(), "Modelo do veículo do cliente");
        validationUtil.validarCampoVazio(request.anoVeiculo(), "Ano do veículo do cliente");
        if (request.anoVeiculo() <= 1000) {
            throw new BadRequestException("Ano do veículo inválido!");
        }
    }

}
