package com.inved.service;

import com.inved.exception.ArquivoAmazonException;

public interface ArquivoAmazonService {

    String buscarUrlArquivoAmazon(String caminhoDiretorio) throws ArquivoAmazonException;

}
