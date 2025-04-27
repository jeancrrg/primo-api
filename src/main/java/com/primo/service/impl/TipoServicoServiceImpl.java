package com.primo.service.impl;

import com.primo.domain.cadastro.TipoServico;
import com.primo.dto.response.TipoServicoResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.exception.NotFoundException;
import com.primo.repository.TipoServicoRepository;
import com.primo.service.TipoServicoService;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServicoServiceImpl implements TipoServicoService {

    private final TipoServicoRepository tipoServicoRepository;
    private final ValidationUtil validationUtil;

    public TipoServicoServiceImpl(TipoServicoRepository tipoServicoRepository, ValidationUtil validationUtil) {
        this.tipoServicoRepository = tipoServicoRepository;
        this.validationUtil = validationUtil;
    }

    public List<TipoServicoResponse> buscar(Integer codigo, String descricao) {
        try {
            final var listaTiposServicoResponse = tipoServicoRepository.buscar(codigo, descricao);
            if (validationUtil.isEmptyList(listaTiposServicoResponse)) {
                throw new NotFoundException("Falha ao buscar os tipos de serviço! Nenhum tipo de serviço encontrado", this);
            }
            return listaTiposServicoResponse;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os tipos de serviço!", "Código: " + codigo + " Descrição: " + descricao, e.getMessage(), this);
        }
    }

    public TipoServico buscarPeloCodigo(Integer codigo) throws BadRequestException, InternalServerErrorException {
        try {
            validationUtil.validarCampoVazio(codigo, "Código do tipo do serviço");
            return tipoServicoRepository.findByCodigo(codigo);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao buscar o tipo de serviço pelo código! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o tipo de serviço: " + codigo + "! - " + e.getMessage());
        }
    }

}
