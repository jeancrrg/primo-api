package com.primo.service.impl;

import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.cadastro.Usuario;
import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.security.TokenService;
import com.primo.service.AutenticacaoService;
import com.primo.service.PessoaService;
import com.primo.service.UsuarioService;
import com.primo.util.ValidationUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;
    private final ValidationUtil validationUtil;

    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager,
                                   TokenService tokenService,
                                   PessoaService pessoaService,
                                   UsuarioService usuarioService,
                                   ValidationUtil validationUtil) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
        this.validationUtil = validationUtil;
    }

    public LoginResponse realizarLogin(LoginRequest loginRequest) {
        try {
            final var usuarioSenha = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());
            final var autenticacao = authenticationManager.authenticate(usuarioSenha);
            final Usuario usuario = (Usuario) autenticacao.getPrincipal();
            final var token = tokenService.gerarToken(usuario);
            return new LoginResponse(token);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o login!", "Login: " + loginRequest.login(), e.getMessage(), this);
        }
    }

    public void realizarCadastroCliente(CadastroClienteRequest cadastroClienteRequest) {
        try {
            validarCamposCadastroCliente(cadastroClienteRequest);
            validarPossuiCadastroLogin(cadastroClienteRequest.email());
            final Pessoa pessoa = pessoaService.salvar(cadastroClienteRequest.nome(), cadastroClienteRequest.telefone(), cadastroClienteRequest.email());
            final String senha = new BCryptPasswordEncoder().encode(cadastroClienteRequest.senha());
            usuarioService.salvar(pessoa.getCodigo(), cadastroClienteRequest.email(), senha, PermissaoUsuario.USUARIO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao cadastrar o cliente! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do cliente!", "Nome: " + cadastroClienteRequest.nome(), e.getMessage(), this);
        }
    }

    private void validarCamposCadastroCliente(CadastroClienteRequest cadastroClienteRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(cadastroClienteRequest, "Informações do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.nome(), "Nome do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.telefone(), "Telefone do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.email(), "Email do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.senha(), "Senha do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.modeloVeiculo(), "Modelo do veículo do cliente");
        validationUtil.validarCampoVazio(cadastroClienteRequest.anoVeiculo(), "Ano do veículo do cliente");
        if (cadastroClienteRequest.anoVeiculo() <= 1000) {
            throw new BadRequestException("Ano do veículo inválido!");
        }
    }

    private void validarPossuiCadastroLogin(String login) {
        if (usuarioService.verificarPossuiCadastroLogin(login)) {
            throw new BadRequestException("Usuário com login: " + login + " já possui cadastro!");
        }
    }

    public void realizarCadastroPrestador(CadastroPrestadorRequest cadastroPrestadorRequest) {
        try {
            validarPossuiCadastroLogin(cadastroPrestadorRequest.email());
            //usuarioService.salvar(usuario);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao cadastrar o prestador de serviço! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do prestador de serviço!", "Nome: " /*+ cadastroPrestadorRequest.nome()*/, e.getMessage(), this);
        }
    }

}
