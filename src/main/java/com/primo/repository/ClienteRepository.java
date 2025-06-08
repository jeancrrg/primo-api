package com.primo.repository;

import com.primo.domain.cadastro.Cliente;
import com.primo.dto.response.ClienteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
            SELECT new com.primo.dto.response.ClienteResponse(cli.codigo, pes.nome, pes.telefone, pes.email, pes.cpfCnpj, vcl.modelo, vcl.ano, cli.codigoAvatar)
              FROM Cliente cli
             INNER JOIN Usuario usr ON usr.codigoPessoa = cli.codigo
             INNER JOIN Pessoa pes ON pes.codigo = cli.codigo
             INNER JOIN Veiculo vcl ON vcl.codigoPessoa = pes.codigo
             WHERE 1=1
               AND usr.indicadorAtivo = true
               AND cli.indicadorAtivo = true
               AND cli.codigo = :codigo
    """)
    ClienteResponse buscar(Long codigo);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Cliente cli
               SET cli.codigoAvatar = :codigoAvatar
             WHERE cli.codigo = :codigo
    """)
    void atualizarAvatar(Long codigo, Integer codigoAvatar);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Cliente cli
               SET cli.indicadorAtivo = false
             WHERE cli.codigo = :codigo
    """)
    void inativar(Long codigo);

}
