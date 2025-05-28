package com.primo.repository;

import com.primo.domain.cadastro.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByCodigo(Long codigo);

    boolean existsByCpfCnpj(String cpfCnpj);

}
