package com.primo.service.impl;

import com.primo.domain.cadastro.Usuario;
import com.primo.domain.builder.UsuarioBuilder;
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

    public LoginResponse realizarLogin(LoginRequest loginRequest) throws BadRequestException {
        var usuarioSenha = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());
        var autenticacao = authenticationManager.authenticate(usuarioSenha);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        var token = tokenService.gerarToken(usuario);
        return new LoginResponse(token);
    }

    public void realizarCadastroPrestador(CadastroPrestadorRequest cadastroPrestadorRequest) throws BadRequestException, InternalServerErrorException {
        if (usuarioService.verificarPossuiCadastro(cadastroPrestadorRequest.login())) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse usuário!");
        }
        UserDetails userDetails = segurancaService.loadUserByUsername(cadastroPrestadorRequest.login());
        if (userDetails != null) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse login!");
        }

        Usuario usuario = UsuarioBuilder.builder()
                .codigoPessoa(1L)
                .email(cadastroPrestadorRequest.login())
                .senha(new BCryptPasswordEncoder().encode(cadastroPrestadorRequest.senha()))
                .permissao(PermissaoUsuario.USUARIO)
                .indicadorAtivo(true)
                .build();
        usuarioService.salvar(usuario);
    }

}
