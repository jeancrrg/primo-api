package com.primo.service.impl;

import com.primo.domain.cadastro.Veiculo;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.VeiculoRepository;
import com.primo.service.VeiculoService;
import com.primo.util.FormatterUtil;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ValidationUtil validationUtil;
    private final FormatterUtil formatterUtil;

    public VeiculoServiceImpl(VeiculoRepository veiculoRepository, ValidationUtil validationUtil, FormatterUtil formatterUtil) {
        this.veiculoRepository = veiculoRepository;
        this.validationUtil = validationUtil;
        this.formatterUtil = formatterUtil;
    }

    public void salvar(Long codigoPessoa, String modeloVeiculo, Integer anoVeiculo) throws BadRequestException, InternalServerErrorException {
        try {
            validarCamposVeiculo(codigoPessoa, modeloVeiculo, anoVeiculo);
            final Veiculo veiculo = Veiculo.builder()
                    .codigoPessoa(codigoPessoa)
                    .modelo(formatterUtil.removerAcentos(modeloVeiculo).trim().toUpperCase())
                    .ano(anoVeiculo)
                    .build();
            veiculoRepository.save(veiculo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de salvar o veículo! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao salvar o veículo: " + modeloVeiculo + " da pessoa: " + codigoPessoa + "! - " + e.getMessage());
        }
    }

    private void validarCamposVeiculo(Long codigoPessoa, String modeloVeiculo, Integer anoVeiculo) throws BadRequestException {
        validationUtil.validarCampoVazio(codigoPessoa, "Código da pessoa");
        validationUtil.validarCampoVazio(modeloVeiculo, "Modelo do veículo");
        validationUtil.validarCampoVazio(anoVeiculo, "Ano do veículo");
    }

}
