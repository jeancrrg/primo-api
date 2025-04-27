package com.primo.dto.request;

/**
 * @author Jean Garcia
 * @param nome
 * @param telefone
 * @param email
 * @param senha
 * @param modeloVeiculo
 * @param anoVeiculo
 */
public record CadastroClienteRequest(String nome, String telefone, String email, String senha, String modeloVeiculo, Integer anoVeiculo) {

}
