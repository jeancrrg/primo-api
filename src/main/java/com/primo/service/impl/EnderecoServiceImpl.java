package com.primo.service.impl;

import com.primo.domain.cadastro.Endereco;
import com.primo.dto.EnderecoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.EnderecoRepository;
import com.primo.service.EnderecoService;
import com.primo.util.FormatterUtil;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ValidationUtil validationUtil;
    private final FormatterUtil formatterUtil;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, ValidationUtil validationUtil, FormatterUtil formatterUtil) {
        this.enderecoRepository = enderecoRepository;
        this.validationUtil = validationUtil;
        this.formatterUtil = formatterUtil;
    }

    public EnderecoDTO buscarPeloCodigoPessoa(Long codigoPessoa) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoPessoa == null) {
                throw new BadRequestException("Falha ao validar ao buscar o endereço! Código não informado!", this);
            }
            return enderecoRepository.buscarPeloCodigoPessoa(codigoPessoa);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o endereço da pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

    public void salvar(Long codigoPessoa, String logradouro) throws BadRequestException, InternalServerErrorException {
        try {
            validarCamposObrigatoriosEndereco(codigoPessoa, logradouro);
            final var endereco = Endereco.builder()
                    .codigoPessoa(codigoPessoa)
                    .logradouro(formatterUtil.removerAcentos(logradouro).trim().toUpperCase())
                    .nomeCidade("UBERLANDIA")
                    .siglaEstado("MG")
                    .build();
            enderecoRepository.save(endereco);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar ao salvar o endereço! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o endereço da pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

    private void validarCamposObrigatoriosEndereco(Long codigoPessoa, String logradouro) {
        validationUtil.validarCampoVazio(codigoPessoa, "Código da pessoa do endereço");
        validationUtil.validarCampoVazio(logradouro, "Logradouro do endereço");
    }

}
