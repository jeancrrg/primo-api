package com.inved.domain.pedido;

import com.inved.domain.embeddabledid.ProdutoCarrinhoId;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TPROCARRINHO")
public class ProdutoCarrinho implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProdutoCarrinhoId produtoCarrinhoId;

    @Column(name = "QNTPRO")
    private Integer quantidadeProduto;

    @Column(name = "VLRSUBTOT")
    private BigDecimal valorSubtotalProduto;

    @Column(name = "DATULTALT")
    private LocalDateTime dataUltimaAlteracao;

    @Transient
    private BigDecimal valorTotalCarrinho;

    public ProdutoCarrinhoId getProdutoCarrinhoId() {
        return produtoCarrinhoId;
    }

    public void setProdutoCarrinhoId(ProdutoCarrinhoId produtoCarrinhoId) {
        this.produtoCarrinhoId = produtoCarrinhoId;
    }

    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public BigDecimal getValorSubtotalProduto() {
        if (valorSubtotalProduto == null) {
            valorSubtotalProduto = BigDecimal.ZERO;
        }
        return valorSubtotalProduto;
    }

    public void setValorSubtotalProduto(BigDecimal valorSubtotalProduto) {
        this.valorSubtotalProduto = valorSubtotalProduto;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public BigDecimal getValorTotalCarrinho() {
        if (valorTotalCarrinho == null) {
            valorTotalCarrinho = BigDecimal.ZERO;
        }
        valorTotalCarrinho = valorTotalCarrinho.add(valorSubtotalProduto);
        return valorTotalCarrinho;
    }

    public void setValorTotalCarrinho(BigDecimal valorTotalCarrinho) {
        this.valorTotalCarrinho = valorTotalCarrinho;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProdutoCarrinho produtoCarrinho = (ProdutoCarrinho) o;
        return Objects.equals(produtoCarrinhoId, produtoCarrinho.produtoCarrinhoId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(produtoCarrinhoId);
    }

}
