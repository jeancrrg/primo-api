package com.primo.service;

import com.primo.domain.cadastro.TipoServico;
import com.primo.dto.response.TipoServicoResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

import java.util.List;

public interface TipoServicoService {

    List<TipoServicoResponse> buscar(Integer codigo, String descricao);

    TipoServico buscarPeloCodigo(Integer codigo) throws BadRequestException, InternalServerErrorException;

}
