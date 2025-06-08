package com.primo.domain.cadastro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TCLIENTE")
public class Cliente {

    @Id
    @Column(name = "CODPES")
    private Long codigo;

    @Column(name = "CODAVA")
    private Integer codigoAvatar;

    @Column(name = "INDATV")
    private Boolean indicadorAtivo;

}
