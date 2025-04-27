package com.primo.domain.builder;

import com.primo.domain.cadastro.Usuario;
import com.primo.domain.enums.PermissaoUsuario;

/**
 * @author Jean Garcia
 */
public class UsuarioBuilder {

    private final Usuario usuario;

    private UsuarioBuilder() {
        usuario = new Usuario();
    }

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public UsuarioBuilder codigoUsuario(Long codigoUsuario) {
        this.usuario.setCodigoUsuario(codigoUsuario);
        return this;
    }

    public UsuarioBuilder codigoPessoa(Long codigoPessoa) {
        this.usuario.setCodigoPessoa(codigoPessoa);
        return this;
    }

    public UsuarioBuilder email(String email) {
        this.usuario.setLogin(email);
        return this;
    }

    public UsuarioBuilder senha(String senha) {
        this.usuario.setSenha(senha);
        return this;
    }

    public UsuarioBuilder permissao(PermissaoUsuario permissao) {
        this.usuario.setPermissao(permissao);
        return this;
    }

    public UsuarioBuilder indicadorAtivo(Boolean indicadorAtivo) {
        this.usuario.setIndicadorAtivo(indicadorAtivo);
        return this;
    }

    public Usuario build() {
            return this.usuario;
        }

}
