package com.primo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {

    public void error(String mensagemErro, String detalheErro, String erro, Object objetoErro) {
        final Class<?> classe = objetoErro.getClass();
        final Logger logger = LoggerFactory.getLogger(classe);
        logger.error("[MENSAGEM ERRO]: {} - [DETALHE]: {} - [ERRO]: {}", mensagemErro, detalheErro, erro);
    }

    public void error(String mensagemErro, String detalheErro, String erro, Class<?> classe) {
        final Logger logger = LoggerFactory.getLogger(classe);
        logger.error("[MENSAGEM ERRO]: {} - [TIPO ERRO]: {} - [DETALHE]: {} - [ERRO]: {}", mensagemErro, classe.getSimpleName(), detalheErro, erro);
    }

    public void error(String mensagemErro, Object objetoErro) {
        final Class<?> classe = objetoErro.getClass();
        final Logger logger = LoggerFactory.getLogger(classe);
        logger.error("[MENSAGEM ERRO]: {}", mensagemErro);
    }

}
