package com.primo.repository;

import com.primo.domain.PrestadorServico;
import com.primo.domain.dto.PrestadorServicoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Long> {

    @Query("SELECT new com.primo.domain.dto.PrestadorServicoDTO(pes.codigo, pes.nome, ptd.tipoServico.codigo, ptd.tipoServico.descricao, ptd.valorServico) " +
            " FROM PrestadorServico ptd, " +
            "      Pessoa pes " +
            "WHERE 1=1 " +
            "  AND (:termoBusca IS NULL OR (pes.nome LIKE :termoBusca OR ptd.tipoServico.descricao LIKE :termoBusca)) " +
            "  AND ptd.codigoPessoa = pes.codigo ")
    List<PrestadorServicoDTO> buscar(String termoBusca);

}
