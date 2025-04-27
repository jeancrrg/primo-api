package com.primo.service;

import com.primo.dto.response.TipoServicoResponse;

import java.util.List;

public interface TipoServicoService {

    List<TipoServicoResponse> buscar(Integer codigo, String descricao);

}
