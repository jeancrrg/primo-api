package com.primo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Jean Garcia
 * @param codigo
 * @param descricao
 */
public record TipoServicoResponse(Integer codigo, String descricao) {

}
