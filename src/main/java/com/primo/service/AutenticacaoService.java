package com.primo.service;

import com.primo.domain.dto.AutenticacaoRequest;
import com.primo.domain.dto.AutenticacaoResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface AutenticacaoService {

    AutenticacaoResponse realizarLogin(AutenticacaoRequest loginDTO) throws BadRequestException;

    void cadastrar(AutenticacaoRequest loginDTO) throws BadRequestException, InternalServerErrorException;

}
