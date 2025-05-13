package com.primo.controller;

import com.primo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> buscarUsuarioCliente(@RequestParam Long codigoUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuarioCliente(codigoUsuario));
    }

}
