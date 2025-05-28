package com.primo.repository;

import com.primo.domain.cadastro.Cliente;
import com.primo.dto.response.ClienteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
            SELECT new com.primo.dto.response.ClienteResponse(cli.codigoPessoa, pes.nome, pes.telefone, pes.email, pes.cpfCnpj, vcl.modelo, vcl.ano, cli.codigoAvatar)
              FROM Cliente cli
             INNER JOIN Usuario usr ON usr.codigoPessoa = cli.codigoPessoa
             INNER JOIN Pessoa pes ON pes.codigo = cli.codigoPessoa
             INNER JOIN Veiculo vcl ON vcl.codigoPessoa = pes.codigo
             WHERE 1=1
               AND usr.indicadorAtivo = true
               AND cli.indicadorAtivo = true
               AND cli.codigoPessoa = :codigoPessoa
    """)
    ClienteResponse buscar(Long codigoPessoa);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Cliente cli
               SET cli.codigoAvatar = :codigoAvatar
             WHERE cli.codigoPessoa = :codigoPessoa
    """)
    void atualizarAvatar(Long codigoPessoa, Integer codigoAvatar);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Cliente cli
               SET cli.indicadorAtivo = false
             WHERE cli.codigoPessoa = :codigoPessoa
    """)
    void inativar(Long codigoPessoa);

}
