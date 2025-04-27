package com.primo.service.impl;

import com.primo.domain.cadastro.Pessoa;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.PessoaRepository;
import com.primo.service.PessoaService;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ValidationUtil validationUtil;

    public PessoaServiceImpl(PessoaRepository pessoaRepository, ValidationUtil validationUtil) {
        this.pessoaRepository = pessoaRepository;
        this.validationUtil = validationUtil;
    }

    public Pessoa salvar(String nome, String cpfCnpj, String telefone, String email) throws BadRequestException, InternalServerErrorException {
        try {
            validarCamposObrigatoriosPessoa(nome, telefone, email);
            final Pessoa pessoa = Pessoa.builder()
                    .nome(nome)
                    .cpfCnpj(cpfCnpj)
                    .telefone(telefone)
                    .email(email)
                    .dataCadastro(LocalDateTime.now())
                    .build();
            return pessoaRepository.save(pessoa);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao salvar a pessoa! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar a pessoa!", "Nome: " + nome, e.getMessage(), this);
        }
    }

    private void validarCamposObrigatoriosPessoa(String nome, String telefone, String email) throws BadRequestException {
        validationUtil.validarCampoVazio(nome, "Nome da pessoa");
        validationUtil.validarCampoVazio(telefone, "Telefone da pessoa");
        validationUtil.validarCampoVazio(email, "email da pessoa");
    }

}
