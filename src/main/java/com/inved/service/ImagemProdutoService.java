package com.inved.service;

import com.inved.domain.cadastro.ImagemProduto;
import com.inved.exception.ArquivoAmazonException;
import com.inved.exception.BadRequestException;
import com.inved.exception.ConverterException;
import com.inved.exception.InternalServerErrorException;

import java.util.List;

public interface ImagemProdutoService {

    List<ImagemProduto> buscar(Long codigo, String nome, Long codigoProduto) throws ConverterException,
                                                                                    ArquivoAmazonException, InternalServerErrorException;

    List<String> buscarUrlImagensProduto(Long codigoProduto) throws BadRequestException, ConverterException,
                                                                    ArquivoAmazonException, InternalServerErrorException;

}
