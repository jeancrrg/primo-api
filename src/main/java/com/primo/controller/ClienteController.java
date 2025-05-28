package com.primo.controller;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.request.ClienteRequest;
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

    @GetMapping("/{codigoPessoa}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long codigoPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscar(codigoPessoa));
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody ClienteRequest request) {
        clienteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{codigoPessoa}/avatar")
    public ResponseEntity<?> atualizarAvatar(@PathVariable Long codigoPessoa, @RequestBody AvatarRequest avatarRequest) {
        clienteService.atualizarAvatar(codigoPessoa, avatarRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigoPessoa}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long codigoPessoa) {
        clienteService.inativar(codigoPessoa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
