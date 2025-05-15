package com.primo.controller;

import com.primo.dto.response.AvatarResponse;
import com.primo.service.AvatarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avatares")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public ResponseEntity<List<AvatarResponse>> buscar(@RequestParam(required = false) Integer codigo) {
        return ResponseEntity.status(HttpStatus.OK).body(avatarService.buscar(codigo));
    }

}
