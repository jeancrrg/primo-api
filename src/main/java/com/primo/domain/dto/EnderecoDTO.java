package com.primo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Jean Garcia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String logradouro;
    private String nomeBairro;
    private String latitude;
    private String longitude;

}
