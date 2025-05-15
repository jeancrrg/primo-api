package com.primo.service;

import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.response.ClienteResponse;

public interface ClienteService {

    ClienteResponse buscar(Long codigoPessoa);

    void cadastrar(CadastroClienteRequest request);

    void atualizarAvatar(Long codigoPessoa, Integer codigoAvatar);

}
