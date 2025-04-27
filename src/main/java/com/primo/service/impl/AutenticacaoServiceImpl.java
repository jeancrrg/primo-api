package com.primo.service.impl;

import com.primo.domain.cadastro.Usuario;
import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.exception.InternalServerErrorException;
import com.primo.security.TokenService;
import com.primo.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ClienteService clienteService;
    private final PrestadorServicoService prestadorServicoService;

    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager,
                                   TokenService tokenService,
                                   ClienteService clienteService,
                                   PrestadorServicoService prestadorServicoService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.clienteService = clienteService;
        this.prestadorServicoService = prestadorServicoService;
    }

    public LoginResponse realizarLogin(LoginRequest request) {
        try {
            final var usuarioSenha = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
            final var autenticacao = authenticationManager.authenticate(usuarioSenha);
            final Usuario usuario = (Usuario) autenticacao.getPrincipal();
            final var token = tokenService.gerarToken(usuario);
            return new LoginResponse(token);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o login!", "Login: " + request.login(), e.getMessage(), this);
        }
    }

    public void realizarCadastroCliente(CadastroClienteRequest request) {
        clienteService.cadastrar(request);
    }

    public void realizarCadastroPrestador(CadastroPrestadorRequest request) {
        prestadorServicoService.cadastrar(request);
    }

}
