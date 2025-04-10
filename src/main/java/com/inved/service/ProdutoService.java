package com.inved.service;

import com.inved.domain.cadastro.Produto;
import com.inved.domain.dto.ProdutoDTO;
import com.inved.exception.BadRequestException;
import com.inved.exception.InternalServerErrorException;

import java.util.List;

public interface ProdutoService {

     List<ProdutoDTO> buscarAtivo(Long codigo, String nome) throws InternalServerErrorException;

     Produto buscarPeloCodigo(Long codigoProduto) throws BadRequestException, InternalServerErrorException;

     Integer buscarQuantidadeEstoque(Long codigoProduto) throws BadRequestException, InternalServerErrorException;

}
