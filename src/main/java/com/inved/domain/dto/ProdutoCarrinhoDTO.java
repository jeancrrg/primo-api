package com.inved.domain.dto;

import com.inved.domain.pedido.ProdutoCarrinho;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
public class ProdutoCarrinhoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String nome;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private List<String> listaUrlImagens;
    private Integer avaliacaoProduto;
    private String nomeCategoria;
    private Integer quantidadeProduto;
    private BigDecimal valorSubtotalProduto;
    private BigDecimal valorTotalCarrinho;
    private Long codigoCliente;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public List<String> getListaUrlImagens() {
        return listaUrlImagens;
    }

    public void setListaUrlImagens(List<String> listaUrlImagens) {
        this.listaUrlImagens = listaUrlImagens;
    }

    public Integer getAvaliacaoProduto() {
        return avaliacaoProduto;
    }

    public void setAvaliacaoProduto(Integer avaliacaoProduto) {
        this.avaliacaoProduto = avaliacaoProduto;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public BigDecimal getValorSubtotalProduto() {
        return valorSubtotalProduto;
    }

    public void setValorSubtotalProduto(BigDecimal valorSubtotalProduto) {
        this.valorSubtotalProduto = valorSubtotalProduto;
    }

    public BigDecimal getValorTotalCarrinho() {
        return valorTotalCarrinho;
    }

    public void setValorTotalCarrinho(BigDecimal valorTotalCarrinho) {
        this.valorTotalCarrinho = valorTotalCarrinho;
    }

    public Long getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Long codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public ProdutoCarrinhoDTO toProdutoCarrinhoDTO(ProdutoCarrinho produtoCarrinho, List<String> listaUrlImagensProduto) {
        final ProdutoCarrinhoDTO produtoCarrinhoDTO = new ProdutoCarrinhoDTO();
        produtoCarrinhoDTO.setCodigo(produtoCarrinho.getProdutoCarrinhoId().getProduto().getCodigo());
        produtoCarrinhoDTO.setNome(produtoCarrinho.getProdutoCarrinhoId().getProduto().getNome());
        produtoCarrinhoDTO.setPreco(produtoCarrinho.getProdutoCarrinhoId().getProduto().getPreco());
        produtoCarrinhoDTO.setQuantidadeEstoque(produtoCarrinho.getProdutoCarrinhoId().getProduto().getQuantidadeEstoque());
        produtoCarrinhoDTO.setListaUrlImagens(listaUrlImagensProduto);
        produtoCarrinhoDTO.setAvaliacaoProduto(produtoCarrinho.getProdutoCarrinhoId().getProduto().getAvaliacaoProduto());
        produtoCarrinhoDTO.setNomeCategoria(produtoCarrinho.getProdutoCarrinhoId().getProduto().getCategoria() != null ? produtoCarrinho.getProdutoCarrinhoId().getProduto().getCategoria().getNome()
                                                                                                                        : null);
        produtoCarrinhoDTO.setQuantidadeProduto(produtoCarrinho.getQuantidadeProduto());
        produtoCarrinhoDTO.setValorSubtotalProduto(produtoCarrinho.getValorSubtotalProduto());
        produtoCarrinhoDTO.setValorTotalCarrinho(produtoCarrinho.getValorTotalCarrinho());
        produtoCarrinhoDTO.setCodigoCliente(produtoCarrinho.getProdutoCarrinhoId().getCliente().getCodigo());
        return produtoCarrinhoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProdutoCarrinhoDTO produtoCarrinhoDTO = (ProdutoCarrinhoDTO) o;
        return Objects.equals(codigo, produtoCarrinhoDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
