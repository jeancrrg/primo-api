package com.primo.service;

import com.primo.dto.request.AvatarRequest;
import com.primo.dto.request.ClienteRequest;
import com.primo.dto.response.ClienteResponse;

public interface ClienteService {

    ClienteResponse buscar(Long codigo);

    void cadastrar(ClienteRequest request);

    void atualizarAvatar(Long codigo, AvatarRequest avatarRequest);

    void inativar(Long codigo);

}
