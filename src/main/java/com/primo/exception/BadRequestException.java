package com.primo.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String mensagemErro;
    private final Object objetoErro;

    public BadRequestException(String mensagemErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.objetoErro = null;
    }

    public BadRequestException(String mensagemErro, Object objetoErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.objetoErro = objetoErro;
    }

}
