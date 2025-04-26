package com.primo.service.impl;

import com.primo.domain.Pessoa;
import com.primo.domain.Usuario;
import com.primo.domain.builder.UsuarioBuilder;
import com.primo.domain.dto.AutenticacaoRequest;
import com.primo.domain.dto.AutenticacaoResponse;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.security.SegurancaService;
import com.primo.security.TokenService;
import com.primo.service.AutenticacaoService;
import com.primo.service.PessoaService;
import com.primo.service.UsuarioService;
import com.primo.util.ValidationUtil;
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
    private final ValidationUtil validationUtil;
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;

    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager,
                                   SegurancaService segurancaService,
                                   TokenService tokenService,
                                   ValidationUtil validationUtil,
                                   PessoaService pessoaService,
                                   UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.segurancaService = segurancaService;
        this.tokenService = tokenService;
        this.validationUtil = validationUtil;
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
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
        if (usuarioService.verificarPossuiCadastro(autenticacaoRequest.login())) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse usuário!");
        }
        UserDetails userDetails = segurancaService.loadUserByUsername(autenticacaoRequest.login());
        if (userDetails != null) {
            throw new BadRequestException("Não é possível cadastrar pois já possui cadastro desse login!");
        }

        Usuario usuario = UsuarioBuilder.builder()
                .codigoPessoa(1L)
                .email(autenticacaoRequest.login())
                .senha(new BCryptPasswordEncoder().encode(autenticacaoRequest.senha()))
                .permissao(PermissaoUsuario.USUARIO)
                .indicadorAtivo(true)
                .build();
        usuarioService.salvar(usuario);
    }

}
