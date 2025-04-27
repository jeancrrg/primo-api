package com.primo.service.impl;

import com.primo.domain.cadastro.Usuario;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.UsuarioRepository;
import com.primo.service.UsuarioService;
import com.primo.util.ValidationUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ValidationUtil validationUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ValidationUtil validationUtil) {
        this.usuarioRepository = usuarioRepository;
        this.validationUtil = validationUtil;
    }

    public UserDetails buscarPeloLogin(String login) throws BadRequestException, InternalServerErrorException {
        try {
            validationUtil.validarCampoVazio(login, "Login");
            return usuarioRepository.findByLogin(login);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao buscar o usuário pelo login! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o usuário pelo login: " + login + "! - " + e.getMessage());
        }
    }

    public void validarPossuiCadastroLogin(String login) throws BadRequestException, InternalServerErrorException {
        try {
            if (login == null || login.isEmpty()) {
                throw new BadRequestException("Falha ao verificar se possui o cadastro do usuário pelo login! Login não informado!");
            }
            if (usuarioRepository.existsByLogin(login)) {
                throw new BadRequestException("Usuário com login: " + login + " já possui cadastro!");
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao verificar se possui o cadastro do usuário pelo login: " + login + "! - " + e.getMessage());
        }
    }

    public Usuario salvar(Long codigoPessoa, String login, String senha, PermissaoUsuario permissaoUsuario) throws BadRequestException, InternalServerErrorException {
        try {
            validarCamposUsuario(codigoPessoa, login, senha, permissaoUsuario);
            Usuario usuario = Usuario.builder()
                    .codigoPessoa(codigoPessoa)
                    .login(login)
                    .senha(senha)
                    .permissao(permissaoUsuario)
                    .indicadorAtivo(true)
                    .build();
            return usuarioRepository.save(usuario);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de salvar o usuário! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o usuário com código pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

    private void validarCamposUsuario(Long codigoPessoa, String login, String senha, PermissaoUsuario permissaoUsuario) throws BadRequestException {
        validationUtil.validarCampoVazio(codigoPessoa, "Código da pessoa do usuário");
        validationUtil.validarCampoVazio(login, "Login do usuário");
        validationUtil.validarCampoVazio(senha, "Senha do usuário");
        validationUtil.validarCampoVazio(permissaoUsuario, "Permissão do usuário");
    }

}
