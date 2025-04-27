package com.primo.repository;

import com.primo.domain.cadastro.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
