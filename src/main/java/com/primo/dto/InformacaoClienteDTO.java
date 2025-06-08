package com.primo.dto;

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
public class InformacaoClienteDTO {

    private Long codigo;
    private String nome;
    private Integer codigoAvatar;

}
