package com.inved.domain.builder;

import com.inved.domain.embeddabledid.ProdutoCarrinhoId;
import com.inved.domain.pedido.ProdutoCarrinho;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Jean Garcia
 */
public final class ProdutoCarrinhoBuilder {

    private final ProdutoCarrinho produtoCarrinho;

    private ProdutoCarrinhoBuilder() {
        produtoCarrinho = new ProdutoCarrinho();
    }

    public static ProdutoCarrinhoBuilder builder() {
        return new ProdutoCarrinhoBuilder();
    }

    public ProdutoCarrinhoBuilder produtoCarrinhoId(ProdutoCarrinhoId produtoCarrinhoId) {
        this.produtoCarrinho.setProdutoCarrinhoId(produtoCarrinhoId);
        return this;
    }

    public ProdutoCarrinhoBuilder quantidadeProduto(Integer quantidadeProduto) {
        this.produtoCarrinho.setQuantidadeProduto(quantidadeProduto);
        return this;
    }

    public ProdutoCarrinhoBuilder valorSubtotalProduto(BigDecimal valorSubtotalProduto) {
        this.produtoCarrinho.setValorSubtotalProduto(valorSubtotalProduto);
        return this;
    }

    public ProdutoCarrinhoBuilder dataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.produtoCarrinho.setDataUltimaAlteracao(dataUltimaAlteracao);
        return this;
    }

    public ProdutoCarrinhoBuilder valorTotalCarrinho(BigDecimal valorTotalCarrinho) {
        this.produtoCarrinho.setValorTotalCarrinho(valorTotalCarrinho);
        return this;
    }

    public ProdutoCarrinho build() {
        return this.produtoCarrinho;
    }

}
