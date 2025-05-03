package com.primo.dto.request;

import java.math.BigDecimal;

/**
 * @author Jean Garcia
 * @param nome
 * @param telefone
 * @param email
 * @param senha
 * @param cnpj
 * @param endereco
 * @param codigoTipoServico
 * @param valorServico
 */
public record CadastroPrestadorRequest(String nome, String telefone, String email, String senha,
                                       String cnpj, String endereco, Integer codigoTipoServico, BigDecimal valorServico) {

}
