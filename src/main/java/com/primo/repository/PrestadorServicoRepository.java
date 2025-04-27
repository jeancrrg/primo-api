package com.primo.repository;

import com.primo.domain.cadastro.PrestadorServico;
import com.primo.dto.PrestadorServicoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Long> {

    @Query("""
            SELECT new com.primo.dto.PrestadorServicoDTO(pes.codigo, pes.nome, ptd.tipoServico.codigo, ptd.tipoServico.descricao, ptd.valorServico)
             FROM PrestadorServico ptd,
                  Pessoa pes
            WHERE 1=1
              AND (:termoPesquisa IS NULL OR (pes.nome LIKE :termoPesquisa OR ptd.tipoServico.descricao LIKE :termoPesquisa))
              AND ptd.codigoPessoa = pes.codigo
              AND EXISTS (SELECT edr
                            FROM Endereco edr
                           WHERE 1=1
                             AND pes.codigo = edr.codigoPessoa)
            """)
    List<PrestadorServicoDTO> buscar(String termoPesquisa);

}
