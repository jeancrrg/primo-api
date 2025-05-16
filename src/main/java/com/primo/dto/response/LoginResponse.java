package com.primo.dto.response;

import com.primo.domain.enums.TipoPessoa;

/**
 * @author Jean Garcia
 * @param codigoPessoa
 * @param token
 * @param tipoPessoa
 */
public record LoginResponse(Long codigoPessoa, String token, TipoPessoa tipoPessoa) {

}
