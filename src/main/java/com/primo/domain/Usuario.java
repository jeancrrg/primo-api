package com.primo.domain;

import com.primo.domain.enums.PermissaoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TUSUARIO")
public class Usuario implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODUSR")
    private Long codigoUsuario;

    @Column(name = "CODPES")
    private Long codigoPessoa;

    @Column(name = "EMLUSR")
    private String email;

    @Column(name = "SNHUSR")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRMUSR")
    private PermissaoUsuario permissao;

    @Column(name = "INDATV")
    private boolean indicadorAtivo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (PermissaoUsuario.ADMINISTRADOR.equals(permissao)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"), new SimpleGrantedAuthority("ROLE_USUARIO"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
