package com.primo.dto.response;

/**
 * @author Jean Garcia
 * @param codigoPessoa
 * @param nome
 * @param telefone
 * @param email
 * @param modeloVeiculo
 * @param anoVeiculo
 * @param codigoAvatar
 */
public record ClienteResponse(Long codigoPessoa, String nome, String telefone, String email, String modeloVeiculo, int anoVeiculo, Integer codigoAvatar) {

}
