package com.primo.service;

import com.primo.domain.cadastro.Pessoa;
import com.primo.domain.enums.TipoPessoa;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface PessoaService {

    Pessoa buscarPeloCodigo(Long codigo) throws BadRequestException, InternalServerErrorException;

    Pessoa salvar(String nome, String cpfCnpj, String telefone, String email, TipoPessoa tipoPessoa) throws BadRequestException, InternalServerErrorException;

}
