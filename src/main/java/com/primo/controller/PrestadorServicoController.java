package com.primo.controller;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.request.PrestadorRequest;
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

    @GetMapping("/{codigo}")
    public ResponseEntity<PrestadorServicoDTO> buscarPeloCodigo(@PathVariable Long codigo) {
        return ResponseEntity.status(HttpStatus.OK).body(prestadorServicoService.buscarPeloCodigo(codigo));
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody PrestadorRequest prestadorRequest) {
        prestadorServicoService.cadastrar(prestadorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{codigo}/avatar")
    public ResponseEntity<?> atualizarAvatar(@PathVariable Long codigo,  @RequestBody AvatarRequest avatarRequest) {
        prestadorServicoService.atualizarAvatar(codigo, avatarRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigo}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long codigo) {
        prestadorServicoService.inativar(codigo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
