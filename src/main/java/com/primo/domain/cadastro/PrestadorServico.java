package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TPTDSERVICO")
public class PrestadorServico implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODPES")
    private Long codigoPessoa;

    @ManyToOne
    @JoinColumn(name = "CODTIPSRV", referencedColumnName = "CODTIPSRV")
    private TipoServico tipoServico;

    @Column(name = "VLRSRV")
    private BigDecimal valorServico;

}
