package com.primo.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private final String mensagemErro;
    private final String detalheErro;
    private final String erro;
    private final Object objetoErro;

    public InternalServerErrorException(String mensagemErro, String detalheErro, String erro, Object objetoErro, Throwable cause) {
        super(mensagemErro, cause);
        this.mensagemErro = mensagemErro;
        this.detalheErro = detalheErro;
        this.erro = erro;
        this.objetoErro = objetoErro;
    }

}
