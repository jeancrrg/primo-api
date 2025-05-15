package com.primo.controller;

import com.primo.dto.response.TipoServicoResponse;
import com.primo.service.TipoServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tipos-servico")
public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    public TipoServicoController(TipoServicoService tipoServicoService) {
        this.tipoServicoService = tipoServicoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoServicoResponse>> buscar(@RequestParam(required = false) Integer codigo,
                                                            @RequestParam(required = false) String descricao) {
        return ResponseEntity.status(HttpStatus.OK).body(tipoServicoService.buscar(codigo, descricao));
    }

}
