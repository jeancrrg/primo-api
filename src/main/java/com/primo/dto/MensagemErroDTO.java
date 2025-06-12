package com.primo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Jean Garcia
 */
@Data
@AllArgsConstructor
public class MensagemErroDTO {

    private int codigoErro;
    private HttpStatus status;
    private String mensagem;

}
