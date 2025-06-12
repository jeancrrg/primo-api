package com.primo.exception;

import com.primo.dto.MensagemErroDTO;
import com.primo.util.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private final LoggerUtil loggerUtil;

    public ExceptionHandler(LoggerUtil loggerUtil) {
        this.loggerUtil = loggerUtil;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({BadRequestException.class})
    private ResponseEntity<MensagemErroDTO> retornarBadRequestException(BadRequestException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getObjetoErro());
        MensagemErroDTO mensagemErroDTO = new MensagemErroDTO(400, HttpStatus.BAD_REQUEST, excecao.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErroDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class})
    private ResponseEntity<MensagemErroDTO> retornarNotFoundException(NotFoundException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getObjetoErro());
        MensagemErroDTO mensagemErroDTO = new MensagemErroDTO(404, HttpStatus.NOT_FOUND, excecao.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErroDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ConflictException.class})
    private ResponseEntity<MensagemErroDTO> retornarConflictException(ConflictException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getObjetoErro());
        MensagemErroDTO mensagemErroDTO = new MensagemErroDTO(409, HttpStatus.CONFLICT, excecao.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagemErroDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InternalServerErrorException.class})
    private ResponseEntity<MensagemErroDTO> retornarInternalServerErrorException(InternalServerErrorException excecao) {
        loggerUtil.error(excecao.getMensagemErro(), excecao.getDetalheErro(), excecao.getErro(), excecao.getObjetoErro());
        MensagemErroDTO mensagemErroDTO = new MensagemErroDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, excecao.getMensagemErro());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErroDTO);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    private ResponseEntity<MensagemErroDTO> retornarErroInesperado(Exception excecao) {
        final String mensagem = "Ocorreu um erro inesperado no sistema! Contate o administrador.";
        loggerUtil.error(mensagem, null, excecao.getMessage(), excecao.getClass());
        MensagemErroDTO mensagemErroDTO = new MensagemErroDTO(500, HttpStatus.INTERNAL_SERVER_ERROR, mensagem);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErroDTO);
    }

}
