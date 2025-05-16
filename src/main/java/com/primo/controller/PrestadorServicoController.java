package com.primo.controller;

import com.primo.dto.PrestadorServicoDTO;
import com.primo.service.PrestadorServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestadores-servico")
public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;

    public PrestadorServicoController(PrestadorServicoService prestadorServicoService) {
        this.prestadorServicoService = prestadorServicoService;
    }

    @GetMapping
    public ResponseEntity<List<PrestadorServicoDTO>> buscar(@RequestParam(required = false) String termoPesquisa) {
        return ResponseEntity.status(HttpStatus.OK).body(prestadorServicoService.buscar(termoPesquisa));
    }

    @GetMapping("/unico")
    public ResponseEntity<PrestadorServicoDTO> buscarPeloCodigo(@RequestParam Long codigoPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(prestadorServicoService.buscarPeloCodigo(codigoPessoa));
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> atualizarAvatar(@RequestParam Long codigoPessoa, @RequestParam Integer codigoAvatar) {
        prestadorServicoService.atualizarAvatar(codigoPessoa, codigoAvatar);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
