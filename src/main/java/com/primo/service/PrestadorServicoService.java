package com.primo.service;

import com.primo.dto.PrestadorServicoDTO;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa);

}
