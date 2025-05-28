package com.primo.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final String mensagemErro;
    private final Object objetoErro;

    public ConflictException(String mensagemErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.objetoErro = null;
    }

    public ConflictException(String mensagemErro, Object objetoErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.objetoErro = objetoErro;
    }

}
