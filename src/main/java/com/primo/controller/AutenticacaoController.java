package com.primo.controller;

import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.OK).body(autenticacaoService.realizarLogin(loginRequest));
    }

    @PostMapping("/cadastro/cliente")
    public ResponseEntity<?> realizarCadastroCliente(@RequestBody @Valid CadastroClienteRequest cadastroClienteRequest) {
        autenticacaoService.realizarCadastroCliente(cadastroClienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cadastro/prestador")
    public ResponseEntity<?> realizarCadastroPrestador(@RequestBody @Valid CadastroPrestadorRequest cadastroPrestadorRequest) {
        autenticacaoService.realizarCadastroPrestador(cadastroPrestadorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
