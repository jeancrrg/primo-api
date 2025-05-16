package com.primo.service;

import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.CadastroPrestadorRequest;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa);

    PrestadorServicoDTO buscarPeloCodigo(Long codigoPessa);

    void cadastrar(CadastroPrestadorRequest request);

    void atualizarAvatar(Long codigoPessoa, Integer codigoAvatar);

}
