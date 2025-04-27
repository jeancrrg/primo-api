package com.primo.controller;

import com.primo.service.PrestadorServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestadores-servico")
public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;

    public PrestadorServicoController(PrestadorServicoService prestadorServicoService) {
        this.prestadorServicoService = prestadorServicoService;
    }

    @GetMapping
    public ResponseEntity<?> buscar(@RequestParam(required = false) String termoPesquisa) {
        return ResponseEntity.status(HttpStatus.OK).body(prestadorServicoService.buscar(termoPesquisa));
    }

}
