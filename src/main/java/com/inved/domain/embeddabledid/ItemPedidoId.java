package com.inved.domain.embeddabledid;

import com.inved.domain.cadastro.Produto;
import com.inved.domain.pedido.Pedido;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Embeddable
public class ItemPedidoId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "NUMPED", referencedColumnName = "NUMPED")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "CODITE", referencedColumnName = "CODPRO")
    private Produto produto;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemPedidoId itemPedidoId = (ItemPedidoId) o;
        return Objects.equals(pedido, itemPedidoId.pedido) && Objects.equals(produto, itemPedidoId.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, produto);
    }

}
