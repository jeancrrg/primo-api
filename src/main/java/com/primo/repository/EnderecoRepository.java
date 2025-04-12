package com.primo.repository;

import com.primo.domain.cadastro.Endereco;
import com.primo.domain.cadastro.dto.EnderecoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT new com.primo.domain.cadastro.dto.EnderecoDTO(edr.logradouro, edr.nomeBairro, edr.latitude, edr.longitude) " +
            " FROM Endereco edr " +
            "WHERE 1=1 " +
            "  AND edr.codigoPessoa = :codigoPessoa ")
    EnderecoDTO buscarPeloCodigoPessoa(Long codigoPessoa);

}
