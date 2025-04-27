package com.primo.service.impl;

import com.primo.domain.cadastro.Pessoa;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.service.PessoaService;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

    public Pessoa salvar(Pessoa pessoa) throws BadRequestException, InternalServerErrorException {
        return null;
    }

}
