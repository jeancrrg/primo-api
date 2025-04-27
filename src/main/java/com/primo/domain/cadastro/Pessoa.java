package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Jean Garcia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TPESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODPES")
    private Long codigo;

    @Column(name = "NOMPES")
    private String nome;

    @Column(name = "CPFCNP")
    private String cpfCnpj;

    @Column(name = "NUMTEL")
    private String telefone;

    @Column(name = "EMLPES")
    private String email;

    @Column(name = "NOMEMP")
    private String nomeEmpresa;

    @Column(name = "DATCAD")
    private LocalDateTime dataCadastro;

}
