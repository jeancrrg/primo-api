package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jean Garcia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TVEICULO")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODVCL")
    private Long codigo;

    @Column(name = "CODPES")
    private Long codigoPessoa;

    @Column(name = "MODVCL")
    private String modelo;

    @Column(name = "ANOVCL")
    private Integer ano;

}
