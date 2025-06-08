package com.primo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    private final FiltroSeguranca filtroSeguranca;

    public SegurancaConfig(FiltroSeguranca filtroSeguranca) {
        this.filtroSeguranca = filtroSeguranca;
    }

    @Bean
    public SecurityFilterChain configurarFiltrosSeguranca(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Filtra antes de verificar quais APIs são autorizadas
                .addFilterBefore(filtroSeguranca, UsernamePasswordAuthenticationFilter.class)
                // Quais APIs são autorizadas
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/autenticacoes/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autenticacoes/cadastro/cliente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autenticacoes/cadastro/prestador").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tipos-servico").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ws/prestador/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder gerarSenhaCodificada() {
        return new BCryptPasswordEncoder();
    }

}
