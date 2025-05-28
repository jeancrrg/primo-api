package com.primo.dto.request;

/**
 * @author Jean Garcia
 * @param nome
 * @param telefone
 * @param cpf
 * @param email
 * @param senha
 * @param modeloVeiculo
 * @param anoVeiculo
 */
public record ClienteRequest(String nome, String telefone, String cpf, String email, String senha, String modeloVeiculo, Integer anoVeiculo) {

}
