package com.primo.repository;

import com.primo.domain.cadastro.Usuario;
import com.primo.dto.response.UsuarioClienteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByLogin(String login);

    @Query("""
            SELECT new com.primo.dto.response.UsuarioClienteResponse(usr.codigoUsuario, pes.nome, pes.telefone, pes.email, vcl.modelo, vcl.ano)
              FROM Usuario usr
             INNER JOIN Pessoa pes ON pes.codigo = usr.codigoPessoa
             INNER JOIN Cliente cli ON cli.codigoPessoa = pes.codigo
              LEFT JOIN Veiculo vcl ON vcl.codigoPessoa = pes.codigo
             WHERE 1=1
               AND usr.indicadorAtivo = true
               AND cli.indicadorAtivo = true
               AND usr.codigoUsuario = :codigoUsuario
            """)
    UsuarioClienteResponse buscarUsuarioCliente(Long codigoUsuario);

}
