package com.primo.controller;

import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> realizarLogin(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(autenticacaoService.realizarLogin(loginRequest));
    }

    @PostMapping("/cadastro/prestador")
    public ResponseEntity<?> realizarCadastroPrestador(@RequestBody @Valid CadastroPrestadorRequest cadastroPrestadorRequest) {
        autenticacaoService.realizarCadastroPrestador(cadastroPrestadorRequest);
        return ResponseEntity.ok().build();
    }

}
