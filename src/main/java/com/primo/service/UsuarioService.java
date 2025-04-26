package com.primo.service;

import com.primo.domain.Usuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UserDetails buscarPeloLogin(String email);

    Usuario salvar(Usuario usuario) throws BadRequestException, InternalServerErrorException;

}
