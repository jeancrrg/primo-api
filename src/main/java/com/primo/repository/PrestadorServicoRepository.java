package com.primo.repository;

import com.primo.domain.cadastro.PrestadorServico;
import com.primo.domain.cadastro.dto.PrestadorServicoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Long> {

    @Query("SELECT new com.primo.domain.cadastro.dto.PrestadorServicoDTO(pes.codigo, pes.nome, ptd.tipoServico.codigo, ptd.tipoServico.descricao, ptd.valorServico) " +
            " FROM PrestadorServico ptd, " +
            "      Pessoa pes " +
            "WHERE 1=1 " +
            "  AND ptd.codigoPessoa = pes.codigo ")
    List<PrestadorServicoDTO> buscar();

}
