package com.primo.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationUtil {

    public Boolean isEmptyList(List<?> lista) {
        return lista == null || lista.isEmpty();
    }

}
