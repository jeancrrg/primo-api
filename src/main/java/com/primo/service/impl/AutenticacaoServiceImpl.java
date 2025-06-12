package com.primo.service.impl;

import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.cadastro.Usuario;
import com.primo.domain.enums.TipoPessoa;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.ConflictException;
import com.primo.exception.InternalServerErrorException;
import com.primo.config.security.TokenService;
import com.primo.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PessoaService pessoaService;
    private final ClienteService clienteService;
    private final PrestadorServicoService prestadorServicoService;

    public AutenticacaoServiceImpl(AuthenticationManager authenticationManager,
                                   TokenService tokenService,
                                   PessoaService pessoaService,
                                   ClienteService clienteService,
                                   PrestadorServicoService prestadorServicoService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.pessoaService = pessoaService;
        this.clienteService = clienteService;
        this.prestadorServicoService = prestadorServicoService;
    }

    public LoginResponse realizarLogin(LoginRequest loginRequest) {
        try {
            final Usuario usuario = criarDadosUsuario(loginRequest.login(), loginRequest.senha());
            final var pessoa = pessoaService.buscarPeloCodigo(usuario.getCodigoPessoa());
            validarDadosLogin(pessoa);
            final String token = tokenService.gerarToken(usuario);
            return new LoginResponse(pessoa.getCodigo(), token, pessoa.getTipoPessoa());
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de realizar o login!" + e.getMessage());
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao realizar o login!", "Login: " + loginRequest.login(), e.getMessage(), this);
        }
    }

    private Usuario criarDadosUsuario(String login, String senha) {
        final var usuarioSenha = new UsernamePasswordAuthenticationToken(login, senha);
        final var autenticacao = authenticationManager.authenticate(usuarioSenha);
        return (Usuario) autenticacao.getPrincipal();
    }

    private void validarDadosLogin(Pessoa pessoa) throws BadRequestException, ConflictException {
        if (pessoa == null) {
            throw new BadRequestException("Pessoa não encontrada!");
        }
        if (pessoa.getTipoPessoa() == TipoPessoa.CLIENTE && clienteService.verificarPossuiCadastroInativo(pessoa.getCodigo())) {
            throw new ConflictException("Esse cadastro de cliente está inativo! Realize o cadastro novamente");
        }
        if (pessoa.getTipoPessoa() == TipoPessoa.PRESTADOR && prestadorServicoService.verificarPossuiCadastroInativo(pessoa.getCodigo())) {
            throw new ConflictException("Esse cadastro de prestador está inativo! Realize o cadastro novamente");
        }
    }

}
