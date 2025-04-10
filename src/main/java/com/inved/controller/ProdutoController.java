package com.inved.controller;

import com.inved.exception.BadRequestException;
import com.inved.service.ProdutoService;
import com.inved.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscarAtivo(@RequestParam(required = false) Long codigo,
                                         @RequestParam(required = false) String nome) {
        try {
            return ResponseEntity.ok(produtoService.buscarAtivo(codigo, nome));
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar os produtos!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os produtos! Contacte o suporte.");
        }
    }

    @GetMapping("/estoque")
    public ResponseEntity<?> buscarQuantidadeEstoque(@RequestParam(required = false) Long codigoProduto) {
        try {
            return ResponseEntity.ok(produtoService.buscarQuantidadeEstoque(codigoProduto));
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes ao buscar os produtos!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao validar antes ao buscar os produtos! Entre em contato com o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Erro ao buscar os produtos!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar os produtos! Entre em contato com o suporte.");
        }
    }

}
