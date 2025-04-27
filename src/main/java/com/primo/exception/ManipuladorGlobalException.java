package com.primo.exception;

import com.primo.util.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ManipuladorGlobalException extends ResponseEntityExceptionHandler {

    private final LoggerUtil loggerUtil;

    public ManipuladorGlobalException(LoggerUtil loggerUtil) {
        this.loggerUtil = loggerUtil;
    }

    @ExceptionHandler({BadRequestException.class})
    private ResponseEntity<MensagemErroAPI> retornarBadRequestException(BadRequestException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getObjetoErro());

        MensagemErroAPI mensagemAPI = new MensagemErroAPI(400, HttpStatus.BAD_REQUEST, excecao.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemAPI);
    }

    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<MensagemErroAPI> retornarNotFoundException(NotFoundException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getObjetoErro());

        MensagemErroAPI mensagemAPI = new MensagemErroAPI(404, HttpStatus.NOT_FOUND, excecao.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemAPI);
    }

    @ExceptionHandler({InternalServerErrorException.class})
    private ResponseEntity<MensagemErroAPI> retornarInternalServerErrorException(InternalServerErrorException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getDetalheErro(), excecao.getErro(), excecao.getObjetoErro());

        MensagemErroAPI mensagemAPI = new MensagemErroAPI(500, HttpStatus.INTERNAL_SERVER_ERROR, excecao.getMensagemErro());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemAPI);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<MensagemErroAPI> retornarErroInesperado(Exception excecao) {
        final String mensagem = "Ocorreu um erro inesperado no sistema! Contate o administrador.";
        loggerUtil.error(mensagem, null, excecao.getMessage(), excecao.getClass());

        MensagemErroAPI mensagemAPI = new MensagemErroAPI(500, HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemAPI);
    }

}
