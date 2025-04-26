package com.primo.service;

import com.primo.domain.Pessoa;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws BadRequestException, InternalServerErrorException;

}
