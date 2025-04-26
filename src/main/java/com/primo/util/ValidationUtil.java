package com.primo.util;

import com.primo.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationUtil {

    public void validarCampoVazio(String campo, String nomeCampo) throws BadRequestException {
        if (campo == null || campo.isEmpty()) {
            throw new BadRequestException("Campo: " + nomeCampo + " não informado!");
        }
    }

    public void validarCampoVazio(Object campo, String nomeCampo) throws BadRequestException {
        if (campo == null) {
            throw new BadRequestException("Campo: " + nomeCampo + " não informado!");
        }
    }

    public Boolean isEmptyList(List<?> lista) {
        return lista == null || lista.isEmpty();
    }

}
