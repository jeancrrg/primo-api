package com.primo.controller;

import com.primo.dto.request.SolicitacaoServicoRequest;
import com.primo.service.SolicitacaoServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitacoes-servico")
public class SolicitacaoServicoController {

    private final SolicitacaoServicoService solicitacaoServicoService;

    public SolicitacaoServicoController(SolicitacaoServicoService solicitacaoServicoService) {
        this.solicitacaoServicoService = solicitacaoServicoService;
    }

    @PostMapping
    public ResponseEntity<?> enviarSolicitacao(@RequestBody SolicitacaoServicoRequest solicitacaoServicoRequest) {
        solicitacaoServicoService.enviarSolicitacao(solicitacaoServicoRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
