package com.inved.domain.dto;

import com.inved.domain.cadastro.Produto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
public class ProdutoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codigo;
    private String nome;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String descricaoDetalhada;
    private List<String> listaUrlImagens;
    private Integer avaliacaoProduto;
    private String nomeCategoria;

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

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
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

    public ProdutoDTO toProdutoDTO(Produto produto, List<String> listaUrlImagensProduto) {
        final ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setCodigo(produto.getCodigo());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoDTO.setDescricaoDetalhada(produto.getDescricaoDetalhada());
        produtoDTO.setListaUrlImagens(listaUrlImagensProduto);
        produtoDTO.setAvaliacaoProduto(produto.getAvaliacaoProduto());
        produtoDTO.setNomeCategoria(produto.getCategoria() != null ? produto.getCategoria().getNome() : null);
        return produtoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProdutoDTO produtoDTO = (ProdutoDTO) o;
        return Objects.equals(codigo, produtoDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
