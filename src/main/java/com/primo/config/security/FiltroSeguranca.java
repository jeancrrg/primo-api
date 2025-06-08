package com.primo.config.security;

import com.primo.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public FiltroSeguranca(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Ignorar as requisições WebSocket (handshake)
        String upgradeHeader = request.getHeader("Upgrade");
        if (upgradeHeader != null && upgradeHeader.equalsIgnoreCase("websocket")) {
            filterChain.doFilter(request, response); // libera o handshake direto, sem validar token
            return;
        }

        try {
            var token = recuperarToken(request);
            if (token != null) {
                var login = tokenService.validarToken(token);
                UserDetails userDetails = usuarioService.buscarPeloLogin(login);
                var autenticacao = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Salva o usuário no contexto da autenticação
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
            // Chama o próximo filtro pendente quando não houver token na API, para que dessa forma o Spring retorne 403
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar o filtro de seguranca! - " + e.getMessage());
        }
    }

    private String recuperarToken(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        return header.replace("Bearer ", "");
    }

}
