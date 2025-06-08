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

    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long codigo) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscar(codigo));
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody ClienteRequest clienteRequest) {
        clienteService.cadastrar(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{codigo}/avatar")
    public ResponseEntity<?> atualizarAvatar(@PathVariable Long codigo, @RequestBody AvatarRequest avatarRequest) {
        clienteService.atualizarAvatar(codigo, avatarRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigo}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        clienteService.inativar(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
