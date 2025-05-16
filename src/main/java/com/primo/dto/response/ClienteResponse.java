package com.primo.dto.response;

/**
 * @author Jean Garcia
 * @param codigo
 * @param nome
 * @param telefone
 * @param email
 * @param modeloVeiculo
 * @param anoVeiculo
 * @param codigoAvatar
 */
public record ClienteResponse(Long codigo, String nome, String telefone, String email, String modeloVeiculo, int anoVeiculo, Integer codigoAvatar) {

}
