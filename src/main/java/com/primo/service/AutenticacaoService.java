package com.primo.service;

import com.primo.dto.request.LoginRequest;
import com.primo.dto.response.LoginResponse;

public interface AutenticacaoService {

    LoginResponse realizarLogin(LoginRequest request);

}
