package com.primo.service;

import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface VeiculoService {

    void salvar(Long codigoPessoa, String modeloVeiculo, Integer anoVeiculo) throws BadRequestException, InternalServerErrorException;

}
