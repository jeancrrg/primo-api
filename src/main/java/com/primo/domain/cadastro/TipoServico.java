package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TTIPSERVICO")
public class TipoServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODTIPSRV")
    private Integer codigo;

    @Column(name = "DESTIPSRV")
    private String descricao;

}
