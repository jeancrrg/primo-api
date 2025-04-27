package com.primo.service;

import com.primo.dto.request.CadastroClienteRequest;
import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;

public interface AutenticacaoService {

    LoginResponse realizarLogin(LoginRequest request);

    void realizarCadastroCliente(CadastroClienteRequest request);

    void realizarCadastroPrestador(CadastroPrestadorRequest request);

}
