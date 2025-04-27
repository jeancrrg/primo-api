package com.primo.service;

import com.primo.domain.cadastro.Usuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UserDetails buscarPeloLogin(String login) throws BadRequestException, InternalServerErrorException;

    boolean verificarPossuiCadastro(String email);

    Usuario salvar(Usuario usuario) throws BadRequestException, InternalServerErrorException;

}
