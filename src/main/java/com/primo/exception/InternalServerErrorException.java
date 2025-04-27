package com.primo.exception;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

    private final String mensagemErro;
    private final String detalheErro;
    private final String erro;
    private final Object objetoErro;

    public InternalServerErrorException(String mensagemErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.detalheErro = null;
        this.erro = null;
        this.objetoErro = null;
    }

    public InternalServerErrorException(String mensagemErro, String detalheErro, String erro, Object objetoErro) {
        super(mensagemErro);
        this.mensagemErro = mensagemErro;
        this.detalheErro = detalheErro;
        this.erro = erro;
        this.objetoErro = objetoErro;
    }

}
