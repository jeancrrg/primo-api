package com.inved.domain.embeddabledid;

import com.inved.domain.cadastro.Produto;
import com.inved.domain.pedido.Cliente;
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
public class ProdutoCarrinhoId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
    private Produto produto;

    public ProdutoCarrinhoId() {
    }

    public ProdutoCarrinhoId(Cliente cliente, Produto produto) {
        this.cliente = cliente;
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        final ProdutoCarrinhoId produtoCarrinhoId = (ProdutoCarrinhoId) o;
        return Objects.equals(cliente, produtoCarrinhoId.cliente) && Objects.equals(produto, produtoCarrinhoId.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, produto);
    }

}
