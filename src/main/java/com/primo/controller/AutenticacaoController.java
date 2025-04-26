package com.primo.controller;

import com.primo.domain.dto.AutenticacaoRequest;
import com.primo.exception.BadRequestException;
import com.primo.service.AutenticacaoService;
import com.primo.util.LoggerUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;
    private final LoggerUtil loggerUtil;

    public AutenticacaoController(AutenticacaoService autenticacaoService, LoggerUtil loggerUtil) {
        this.autenticacaoService = autenticacaoService;
        this.loggerUtil = loggerUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid AutenticacaoRequest loginDTO) {
        try {
            return ResponseEntity.ok().body(autenticacaoService.realizarLogin(loginDTO));
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de realizar o login!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha na validação ao realizar o login! - " + e.getMessage());
        } catch (Exception e) {
            loggerUtil.error("Erro inesperado ao realizar o login! Login: " + loginDTO.login() + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao realizar o login!");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AutenticacaoRequest autenticacaoRequest) {
        try {
            autenticacaoService.cadastrar(autenticacaoRequest);
            return ResponseEntity.ok().build();
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de cadastrar!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha na validação ao cadastrar! - " + e.getMessage());
        } catch (Exception e) {
            loggerUtil.error("Erro inesperado ao cadastrar! Login: " + autenticacaoRequest.login() + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao cadastrar!");
        }
    }

}
