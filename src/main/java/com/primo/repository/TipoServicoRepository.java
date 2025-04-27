package com.primo.repository;

import com.primo.domain.cadastro.TipoServico;
import com.primo.dto.response.TipoServicoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoServicoRepository extends JpaRepository<TipoServico, Integer> {

    @Query("""
            SELECT new com.primo.dto.response.TipoServicoResponse(tip.codigo, tip.descricao)
              FROM TipoServico tip
             WHERE 1=1
               AND (:codigo IS NULL OR tip.codigo = :codigo)
               AND (:descricao IS NULL OR tip.descricao LIKE :descricao) 
            """)
    List<TipoServicoResponse> buscar(Integer codigo, String descricao);

    TipoServico findByCodigo(Integer codigo);

}
