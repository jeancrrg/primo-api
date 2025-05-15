package com.primo.repository;

import com.primo.domain.cadastro.Avatar;
import com.primo.dto.response.AvatarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    @Query("""
            SELECT new com.primo.dto.response.AvatarResponse(ava.codigo, ava.urlImagem)
              FROM Avatar ava
             WHERE 1=1
               AND (:codigo IS NULL OR ava.codigo = :codigo)
            """)
    List<AvatarResponse> buscar(Integer codigo);

}
