package com.primo.util;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class FormatterUtil {

    public String formatarTextoParaConsulta(String texto) {
        final String textoFormatadoParaConsulta = "%" + removerAcentos(texto).toUpperCase().trim() + "%";
        return textoFormatadoParaConsulta;
    }

    public String removerAcentos(String texto) {
        String textoFormatado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return textoFormatado.replaceAll("[^\\p{ASCII}]", "");
    }

    public String removerCaracteresNaoNumericos(String texto) {
        return texto.replaceAll("\\D", "");
    }

}
