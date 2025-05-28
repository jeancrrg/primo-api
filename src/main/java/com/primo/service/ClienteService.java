package com.primo.service;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.request.ClienteRequest;
import com.primo.dto.response.ClienteResponse;

public interface ClienteService {

    ClienteResponse buscar(Long codigoPessoa);

    void cadastrar(ClienteRequest request);

    void atualizarAvatar(Long codigoPessoa, AvatarRequest avatarRequest);

    void inativar(Long codigoPessoa);

}
