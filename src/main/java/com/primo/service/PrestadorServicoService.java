package com.primo.service;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.PrestadorRequest;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa);

    PrestadorServicoDTO buscarPeloCodigo(Long codigo);

    void cadastrar(PrestadorRequest request);

    void atualizarAvatar(Long codigo, AvatarRequest avatarRequest);

    void inativar(Long codigo);

    boolean verificarPossuiCadastroInativo(Long codigo);

}
