package com.primo.domain.enums;

/**
 * @author Jean Garcia
 */
public enum PermissaoUsuario {

    ADMINISTRADOR("Administrador"),
    USUARIO("Usuario");

    private String permissao;

    PermissaoUsuario(String permissao) {
        this.permissao = permissao;
    }

    public String getPermissao() {
        return permissao;
    }

}
