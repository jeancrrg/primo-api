package com.primo.repository;

import com.primo.domain.Endereco;
import com.primo.domain.dto.EnderecoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT new com.primo.domain.dto.EnderecoDTO(edr.logradouro, edr.nomeBairro, edr.latitude, edr.longitude) " +
            " FROM Endereco edr " +
            "WHERE 1=1 " +
            "  AND edr.codigoPessoa = :codigoPessoa ")
    EnderecoDTO buscarPeloCodigoPessoa(Long codigoPessoa);

}
