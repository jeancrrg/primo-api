package com.inved.domain.pedido;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TPEDIDO")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NUMPED")
    private Long numero;

    @Column(name = "DATPED")
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "CODSTA", referencedColumnName = "CODSTA")
    private StatusPedido statusPedido;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pedido pedido = (Pedido) o;
        return Objects.equals(numero, pedido.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

}
