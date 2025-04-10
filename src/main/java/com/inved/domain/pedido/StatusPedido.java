package com.inved.domain.pedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TSTAPEDIDO")
public class StatusPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODSTA")
    private Integer codigo;

    @Column(name = "DESSTA")
    private String descricao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StatusPedido statusPedido = (StatusPedido) o;
        return Objects.equals(codigo, statusPedido.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
