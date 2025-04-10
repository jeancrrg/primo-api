package com.inved.controller;

import com.inved.domain.dto.ProdutoCarrinhoDTO;
import com.inved.exception.BadRequestException;
import com.inved.service.PedidoService;
import com.inved.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private LoggerUtil loggerUtil;

    @PutMapping()
    public ResponseEntity<?> atualizar(@RequestParam Long codigoCliente, @RequestBody List<ProdutoCarrinhoDTO> listaProdutosCarrinhoDTO) {
        try {
            //produtoCarrinhoService.atualizar(codigoCliente, listaProdutosCarrinhoDTO);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (BadRequestException e) {
            loggerUtil.error("Falha ao validar antes de atualizar o carrinho do cliente!", e, this);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu uma falha ao validar antes de atualizar o carrinho! Contacte o suporte.");
        } catch (Exception e) {
            loggerUtil.error("Ocorreu um erro inesperado ao atualizar o carrinho do cliente!", e, this);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado ao atualizar o carrinho! Contacte o suporte.");
        }
    }

}
