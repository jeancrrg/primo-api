package com.primo.repository;

import com.primo.domain.cadastro.PrestadorServico;
import com.primo.dto.PrestadorServicoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Long> {

    @Query("""
            SELECT new com.primo.dto.PrestadorServicoDTO(pes.codigo, pes.nome, pes.telefone, pes.email, pes.cpfCnpj,
                                                            ptd.tipoServico.codigo, ptd.tipoServico.descricao, ptd.valorServico, ptd.codigoAvatar)
             FROM PrestadorServico ptd
             INNER JOIN Pessoa pes ON pes.codigo = ptd.codigo
            INNER JOIN Usuario usr ON usr.codigoPessoa = pes.codigo
            WHERE 1=1
              AND usr.indicadorAtivo = true
              AND ptd.indicadorAtivo = true
              AND (:termoPesquisa IS NULL OR (pes.nome LIKE :termoPesquisa OR ptd.tipoServico.descricao LIKE :termoPesquisa))
              AND EXISTS (SELECT edr
                            FROM Endereco edr
                           WHERE 1=1
                             AND pes.codigo = edr.codigoPessoa)
    """)
    List<PrestadorServicoDTO> buscar(String termoPesquisa);

    @Query("""
        SELECT new com.primo.dto.PrestadorServicoDTO(pes.codigo, pes.nome, pes.telefone, pes.email, pes.cpfCnpj,
                                                        ptd.tipoServico.codigo, ptd.tipoServico.descricao, ptd.valorServico, ptd.codigoAvatar)
          FROM PrestadorServico ptd
         INNER JOIN Pessoa pes ON pes.codigo = ptd.codigo
         INNER JOIN Usuario usr ON usr.codigoPessoa = pes.codigo
         WHERE 1=1
           AND usr.indicadorAtivo = true
           AND ptd.indicadorAtivo = true
           AND pes.codigo = :codigo
           AND EXISTS (SELECT edr
                            FROM Endereco edr
                           WHERE 1=1
                             AND pes.codigo = edr.codigoPessoa)
    """)
    PrestadorServicoDTO buscarPeloCodigo(Long codigo);

    @Transactional
    @Modifying
    @Query("""
            UPDATE PrestadorServico ptd
               SET ptd.codigoAvatar = :codigoAvatar
             WHERE ptd.codigo = :codigo
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
