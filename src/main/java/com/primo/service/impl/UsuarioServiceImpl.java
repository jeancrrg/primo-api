package com.primo.service.impl;

import com.primo.domain.cadastro.Usuario;
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

    public UserDetails buscarPeloLogin(String email) {
        validationUtil.validarCampoVazio(email, "email");
        return usuarioRepository.findByEmail(email);
    }

    public boolean verificarPossuiCadastro(String email) {
        validationUtil.validarCampoVazio(email, "email");
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario salvar(Usuario usuario) {
        validarCamposUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    private void validarCamposUsuario(Usuario usuario) {
        validationUtil.validarCampoVazio(usuario, "Usuário");
        validationUtil.validarCampoVazio(usuario.getCodigoPessoa(), "Código da pessoa do usuário");
        validationUtil.validarCampoVazio(usuario.getEmail(), "Email do usuário");
        validationUtil.validarCampoVazio(usuario.getSenha(), "Senha do usuário");
        validationUtil.validarCampoVazio(usuario.getPermissao(), "Permissão do usuário");
        validationUtil.validarCampoVazio(usuario.getIndicadorAtivo(), "Indicador de ativo");
    }

}
