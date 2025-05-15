package com.primo.controller;

import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.service.AutenticacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacoes")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> realizarLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autenticacaoService.realizarLogin(request));
    }

    @PostMapping("/cadastro/cliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody CadastroClienteRequest request) {
        autenticacaoService.cadastrarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cadastro/prestador")
    public ResponseEntity<?> cadastrarPrestador(@RequestBody CadastroPrestadorRequest request) {
        autenticacaoService.cadastrarPrestador(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
