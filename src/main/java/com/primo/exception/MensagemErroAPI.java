package com.primo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class MensagemErroAPI {

    private int codigo;
    private HttpStatus status;
    private String mensagem;

}
