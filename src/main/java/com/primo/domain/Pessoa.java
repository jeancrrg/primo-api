package com.primo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TPESSOA")
public class Pessoa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
