package com.primo.domain.cadastro;

import com.primo.domain.enums.StatusSolicitacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Jean Garcia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TSLCSERVICO")
public class SolicitacaoServico {

    @Id
    @Column(name = "CODSLC")
    private Long codigo;

    @Column(name = "CODPESCLI")
    private Long codigoCliente;

    @Column(name = "CODPESPTD")
    private Long codigoPrestador;

    @Column(name = "DATSLC")
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "STASLC")
    private StatusSolicitacao status;

}
