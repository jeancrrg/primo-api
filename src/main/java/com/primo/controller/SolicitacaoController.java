package com.primo.controller;

import com.primo.config.websocket.PrestadorWebSocketHandler;
import com.primo.util.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final PrestadorWebSocketHandler handler;
    private final LoggerUtil loggerUtil;

    public SolicitacaoController(PrestadorWebSocketHandler handler, LoggerUtil loggerUtil) {
        this.handler = handler;
        this.loggerUtil = loggerUtil;
    }

    @GetMapping
    public ResponseEntity<?> solicitar(@RequestParam(required = false) String codigoSolicitacao) {
        try {
            handler.enviarSolicitacao(101L, codigoSolicitacao);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            loggerUtil.error("Erro ao solicitar!", this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao solicitar! - " + e.getMessage());
        }
    }

}
