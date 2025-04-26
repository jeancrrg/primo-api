package com.primo.controller;

import com.primo.service.PrestadorServicoService;
import com.primo.util.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/prestadores-servico")
public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;
    private final LoggerUtil loggerUtil;

    public PrestadorServicoController(PrestadorServicoService prestadorServicoService, LoggerUtil loggerUtil) {
        this.prestadorServicoService = prestadorServicoService;
        this.loggerUtil = loggerUtil;
    }

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam(required = false) String termoPesquisa) {
        try {
            return ResponseEntity.ok(prestadorServicoService.buscar(termoPesquisa));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar os prestadores de serviço!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os prestadores de serviço!");
        }
    }

}
