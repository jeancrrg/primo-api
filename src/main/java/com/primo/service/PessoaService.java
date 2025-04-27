package com.primo.service;

import com.primo.domain.cadastro.Pessoa;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa) throws BadRequestException, InternalServerErrorException;

}
