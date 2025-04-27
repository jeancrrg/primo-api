package com.primo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorServicoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String nome;
    private Integer codigoTipoServico;
    private String descricaoTipoServico;
    private BigDecimal valorServico;
    private EnderecoDTO endereco;

    public PrestadorServicoDTO(Long codigo, String nome, Integer codigoTipoServico, String descricaoTipoServico, BigDecimal valorServico) {
        this.codigo = codigo;
        this.nome = nome;
        this.codigoTipoServico = codigoTipoServico;
        this.descricaoTipoServico = descricaoTipoServico;
        this.valorServico = valorServico;
    }

}
