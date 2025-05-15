package com.primo.controller;

import com.primo.dto.response.ClienteResponse;
import com.primo.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<ClienteResponse> buscar(@RequestParam Long codigoPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscar(codigoPessoa));
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> atualizarAvatar(@RequestParam Long codigoPessoa, @RequestParam Integer codigoAvatar) {
        clienteService.atualizarAvatar(codigoPessoa, codigoAvatar);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
