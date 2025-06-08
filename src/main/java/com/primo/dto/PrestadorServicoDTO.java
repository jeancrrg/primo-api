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
public class PrestadorServicoDTO {

    private Long codigo;
    private String nome;
    private String telefone;
    private String email;
    private String cnpj;
    private Integer codigoTipoServico;
    private String descricaoTipoServico;
    private BigDecimal valorServico;
    private Integer codigoAvatar;
    private EnderecoDTO endereco;

    public PrestadorServicoDTO(Long codigo, String nome, String telefone, String email, String cnpj,
                               Integer codigoTipoServico, String descricaoTipoServico, BigDecimal valorServico, Integer codigoAvatar) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cnpj = cnpj;
        this.codigoTipoServico = codigoTipoServico;
        this.descricaoTipoServico = descricaoTipoServico;
        this.valorServico = valorServico;
        this.codigoAvatar = codigoAvatar;
    }

}
