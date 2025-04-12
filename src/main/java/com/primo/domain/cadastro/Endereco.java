package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TENDERECO")
public class Endereco implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODPES")
    private Long codigoPessoa;

    @Column(name = "LGDEDR")
    private String logradouro;

    @Column(name = "NOMBAI")
    private String nomeBairro;

    @Column(name = "NOMCID")
    private String nomeCidade;

    @Column(name = "SIGEST")
    private String siglaEstado;

    @Column(name = "LATEDR")
    private String latitude;

    @Column(name = "LNGEDR")
    private String longitude;

}
