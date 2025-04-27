package com.primo.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String mensagemErro;
    private final Object objetoErro;

    public NotFoundException(String mensagemErro, Object objetoErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.objetoErro = objetoErro;
    }

}
