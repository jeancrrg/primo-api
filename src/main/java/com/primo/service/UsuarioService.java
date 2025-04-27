package com.primo.service;

import com.primo.domain.cadastro.Usuario;
import com.primo.domain.enums.PermissaoUsuario;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UserDetails buscarPeloLogin(String login) throws BadRequestException, InternalServerErrorException;;

    void validarPossuiCadastroLogin(String login) throws BadRequestException, InternalServerErrorException;

    Usuario salvar(Long codigoPessoa, String login, String senha, PermissaoUsuario permissaoUsuario) throws BadRequestException, InternalServerErrorException;

}
