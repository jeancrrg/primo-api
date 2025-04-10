package com.inved.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {

    public void error(String mensagemErro, Exception excecao, Object objeto) {
        final Class<?> classe = objeto.getClass();
        final Logger logger = LoggerFactory.getLogger(classe);
        final String nomeMetodo = Thread.currentThread().getStackTrace()[2].getMethodName();
        logger.error("ERRO: {} - MÉTODO DO ERRO: {} - TIPO DO ERRO: {} - MENSAGEM DO ERRO: {}", mensagemErro, nomeMetodo, excecao.getClass().getSimpleName(), excecao.getMessage());
    }

}
