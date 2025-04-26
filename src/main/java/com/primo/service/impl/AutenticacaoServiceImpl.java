package com.primo.service.impl;

import com.primo.domain.Usuario;
import com.primo.domain.dto.AutenticacaoRequest;
import com.primo.domain.dto.AutenticacaoResponse;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.security.SegurancaService;
import com.primo.security.TokenService;
import com.primo.service.AutenticacaoService;
import com.primo.service.UsuarioService;
import com.primo.util.ValidationUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final SegurancaService segurancaService;
    private final TokenService tokenService;

    public AutenticacaoServiceImpl(ValidationUtil validationUtil, AuthenticationManager authenticationManager, UsuarioService usuarioService, SegurancaService segurancaService, TokenService tokenService) {
        this.validationUtil = validationUtil;
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.segurancaService = segurancaService;
        this.tokenService = tokenService;
    }

    public AutenticacaoResponse realizarLogin(AutenticacaoRequest autenticacaoRequest) throws BadRequestException {
        validarCamposAutenticacao(autenticacaoRequest);
        var usuarioSenha = new UsernamePasswordAuthenticationToken(autenticacaoRequest.login(), autenticacaoRequest.senha());
        var autenticacao = authenticationManager.authenticate(usuarioSenha);
        var token = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
        return new AutenticacaoResponse(token);
    }

    private void validarCamposAutenticacao(AutenticacaoRequest autenticacaoRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(autenticacaoRequest.login(), "Login");
        validationUtil.validarCampoVazio(autenticacaoRequest.senha(), "Senha");
    }

    public void cadastrar(AutenticacaoRequest autenticacaoRequest) throws BadRequestException, InternalServerErrorException {
        validarCamposAutenticacao(autenticacaoRequest);
        UserDetails userDetails = segurancaService.loadUserByUsername(autenticacaoRequest.login());
        if (userDetails != null) {
            throw new BadRequestException("Não é possível cadastrar pois já possui o usuário cadastrado!");
        }
        String senhaCodificada = new BCryptPasswordEncoder().encode(autenticacaoRequest.senha());
        Usuario usuario = new Usuario();
        usuario.setCodigoPessoa(1L);
        usuario.setEmail(autenticacaoRequest.login());
        usuario.setSenha(senhaCodificada);
        usuario.setPermissao(PermissaoUsuario.USUARIO);
        usuario.setIndicadorAtivo(true);
        usuarioService.salvar(usuario);
    }

}
