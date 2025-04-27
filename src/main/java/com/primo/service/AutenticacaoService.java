package com.primo.service;

import com.primo.dto.request.CadastroPrestadorRequest;
import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

public interface AutenticacaoService {

    LoginResponse realizarLogin(LoginRequest loginRequest);

    void realizarCadastroPrestador(CadastroPrestadorRequest cadastroPrestadorRequest) throws BadRequestException, InternalServerErrorException;

}
