package com.inved.repository;

import com.inved.domain.pedido.ProdutoCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, Long> {

    @Query("SELECT pro " +
            " FROM ProdutoCarrinho pro " +
            "WHERE 1=1 " +
            "  AND pro.produtoCarrinhoId.cliente.codigo = :codigoCliente ")
    List<ProdutoCarrinho> buscarPeloCodigoCliente(Long codigoCliente);

    @Query("SELECT pro " +
            " FROM ProdutoCarrinho pro " +
            "WHERE 1=1 " +
            "  AND pro.produtoCarrinhoId.produto.codigo = :codigoProduto " +
            "  AND pro.produtoCarrinhoId.cliente.codigo = :codigoCliente ")
    ProdutoCarrinho buscarProdutoCliente(Long codigoProduto, Long codigoCliente);

    @Modifying
    @Query("DELETE FROM ProdutoCarrinho pro " +
            "WHERE pro.produtoCarrinhoId.produto.codigo = :codigoProduto " +
            "AND pro.produtoCarrinhoId.cliente.codigo = :codigoCliente ")
    void excluirProdutoCarrinho(Long codigoProduto, Long codigoCliente);

}
