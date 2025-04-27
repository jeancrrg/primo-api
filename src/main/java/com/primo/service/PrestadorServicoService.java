package com.primo.service;

import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.CadastroPrestadorRequest;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa);

    void cadastrar(CadastroPrestadorRequest request);

}
