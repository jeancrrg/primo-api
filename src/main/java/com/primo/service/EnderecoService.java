package com.primo.service;

import com.primo.dto.EnderecoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface EnderecoService {

    EnderecoDTO buscarPeloCodigoPessoa(Long codigoPessoa) throws BadRequestException, InternalServerErrorException;

}
