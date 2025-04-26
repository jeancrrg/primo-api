package com.primo.security;

import com.primo.exception.InternalServerErrorException;
import com.primo.service.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SegurancaService implements UserDetailsService {

    private final UsuarioService usuarioService;

    public SegurancaService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            return usuarioService.buscarPeloLogin(login);
        } catch (InternalServerErrorException e) {
            throw new RuntimeException("Erro ao carregar o usuário! - " + e.getMessage());
        }
    }

}
