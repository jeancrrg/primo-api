package com.inved.controller;

import com.inved.domain.dto.ProdutoCarrinhoDTO;
import com.inved.exception.BadRequestException;
import com.inved.service.ProdutoCarrinhoService;
import com.inved.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/produtos-carrinho")
public class ProdutoCarrinhoController {

    @Autowired
    private ProdutoCarrinhoService produtoCarrinhoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @GetMapping()
    public ResponseEntity<?> buscar(@RequestParam Long codigoCliente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(produtoCarrinhoService.buscar(codigoCliente));
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de buscar os produtos no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de buscar os produto no carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao buscar os produtos no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao buscar os produtos no carrinho! Contacte o suporte.");
        }
    }

    @GetMapping("/codigos")
    public ResponseEntity<?> buscarCodigoProdutos(@RequestParam Long codigoCliente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(produtoCarrinhoService.buscarCodigoProdutos(codigoCliente));
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de buscar o código de produtos no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de buscar o código de produtos no carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao buscar o código de produtos no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao buscar o código de produtos no carrinho! Contacte o suporte.");
        }
    }

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestParam Long codigoCliente, @RequestBody List<ProdutoCarrinhoDTO> listaProdutosCarrinhoDTO) {
        try {
            produtoCarrinhoService.atualizar(codigoCliente, listaProdutosCarrinhoDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de atualizar o carrinho do cliente!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de atualizar o carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao atualizar o carrinho do cliente!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao atualizar o carrinho! Contacte o suporte.");
        }
    }

    @PostMapping()
    public ResponseEntity<?> adicionar(@RequestParam Long codigoProduto, @RequestParam Integer quantidade, @RequestParam Long codigoCliente) {
        try {
            produtoCarrinhoService.adicionar(codigoProduto, quantidade, codigoCliente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de adicionar o produto: " + codigoProduto + " no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de adicionar o produto ao carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao adicionar o produto: " + codigoProduto + " no carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao adicionar o produto no carrinho! Contacte o suporte.");
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> remover(@RequestParam Long codigoProduto, @RequestParam Long codigoCliente) {
        try {
            produtoCarrinhoService.remover(codigoProduto, codigoCliente);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de remover o produto: " + codigoProduto + " do carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de remover o produto ao carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao remover o produto: " + codigoProduto + " do carrinho do cliente: " + codigoCliente + "!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao remover o produto do carrinho! Contacte o suporte.");
        }
    }

}
