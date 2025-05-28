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

    @GetMapping("/{codigoPessoa}")
    public ResponseEntity<PrestadorServicoDTO> buscarPeloCodigo(@PathVariable Long codigoPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(prestadorServicoService.buscarPeloCodigo(codigoPessoa));
    }

    @PostMapping()
    public ResponseEntity<?> cadastrar(@RequestBody PrestadorRequest request) {
        prestadorServicoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{codigoPessoa}/avatar")
    public ResponseEntity<?> atualizarAvatar(@PathVariable Long codigoPessoa,  @RequestBody AvatarRequest avatarRequest) {
        prestadorServicoService.atualizarAvatar(codigoPessoa, avatarRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{codigoPessoa}/inativar")
    public ResponseEntity<?> inativar(@PathVariable Long codigoPessoa) {
        prestadorServicoService.inativar(codigoPessoa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
