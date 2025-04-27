package com.primo.service.impl;

import com.primo.domain.cadastro.Usuario;
import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.security.SegurancaService;
import com.primo.security.TokenService;
import com.primo.service.AutenticacaoService;
import com.primo.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final SegurancaService segurancaService;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager,
                                   SegurancaService segurancaService,
                                   TokenService tokenService,
                                   UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.segurancaService = segurancaService;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
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


        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o cadastro do cliente!", "Nome: " + cadastroClienteRequest.nome(), e.getMessage(), this);
        }
    }

    public void realizarCadastroPrestador(CadastroPrestadorRequest cadastroPrestadorRequest) {
        if (usuarioService.verificarPossuiCadastro(cadastroPrestadorRequest.email())) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse usuário!", this);
        }
        UserDetails userDetails = segurancaService.loadUserByUsername(cadastroPrestadorRequest.email());
        if (userDetails != null) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse login!", this);
        }

        Usuario usuario = Usuario.builder()
                .codigoPessoa(1L)
                .login(cadastroPrestadorRequest.email())
                .senha(new BCryptPasswordEncoder().encode(cadastroPrestadorRequest.senha()))
                .permissao(PermissaoUsuario.USUARIO)
                .indicadorAtivo(true)
                .build();
        usuarioService.salvar(usuario);
    }

}
