package com.primo.dto.response;

/**
 * @author Jean Garcia
 * @param codigoUsuario
 * @param nome
 * @param telefone
 * @param email
 * @param modeloVeiculo
 * @param anoVeiculo
 */
public record UsuarioClienteResponse(Long codigoUsuario, String nome, String telefone, String email, String modeloVeiculo, int anoVeiculo) {

}
