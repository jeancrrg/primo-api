package com.primo.service;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.PrestadorRequest;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa);

    PrestadorServicoDTO buscarPeloCodigo(Long codigoPessa);

    void cadastrar(PrestadorRequest request);

    void atualizarAvatar(Long codigoPessoa, AvatarRequest avatarRequest);

    void inativar(Long codigoPessoa);

}
