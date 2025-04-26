package com.primo.service.impl;

import com.primo.domain.Usuario;
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

    public UserDetails buscarPeloLogin(String email) throws BadRequestException, InternalServerErrorException {
        try {
            validationUtil.validarCampoVazio(email, "email");
            return usuarioRepository.findByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha na validação ao buscar o usuário pelo email! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o usuário pelo email! - " + e.getMessage());
        }
    }

    public boolean verificarPossuiCadastro(String email) throws BadRequestException, InternalServerErrorException {
        try {
            validationUtil.validarCampoVazio(email, "email");
            return usuarioRepository.existsByEmail(email);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha na validação ao verificar se possui cadastro do usuário pelo email! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao verificar se possui cadastro do usuário pelo email! - " + e.getMessage());
        }
    }

    public Usuario salvar(Usuario usuario) throws BadRequestException, InternalServerErrorException {
        try {
            validarCamposUsuario(usuario);
            return usuarioRepository.save(usuario);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de salvar o usuário! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o usuário com email: " + usuario.getEmail() + "! - " + e.getMessage());
        }
    }

    private void validarCamposUsuario(Usuario usuario) throws BadRequestException {
        validationUtil.validarCampoVazio(usuario, "Usuário");
        validationUtil.validarCampoVazio(usuario.getCodigoPessoa(), "Código da pessoa do usuário");
        validationUtil.validarCampoVazio(usuario.getEmail(), "Email do usuário");
        validationUtil.validarCampoVazio(usuario.getSenha(), "Senha do usuário");
        validationUtil.validarCampoVazio(usuario.getPermissao(), "Permissão do usuário");
        validationUtil.validarCampoVazio(usuario.getIndicadorAtivo(), "Indicador de ativo");
    }

}
