package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author Jean Garcia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TPTDSERVICO")
public class PrestadorServico {

    @Id
    @Column(name = "CODPES")
    private Long codigoPessoa;

    @ManyToOne
    @JoinColumn(name = "CODTIPSRV", referencedColumnName = "CODTIPSRV")
    private TipoServico tipoServico;

    @Column(name = "VLRSRV")
    private BigDecimal valorServico;

    @Column(name = "CODAVA")
    private Integer codigoAvatar;

    @Column(name = "INDATV")
    private Boolean indicadorAtivo;

}
