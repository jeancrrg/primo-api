package com.inved.repository;

import com.inved.domain.cadastro.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT pro " +
            " FROM Produto pro " +
            "WHERE 1=1 " +
            "  AND (:codigo IS NULL OR pro.codigo = :codigo) " +
            "  AND (:nome IS NULL OR pro.nome LIKE :nome) " +
            "  AND pro.statusProduto.codigo = 1 ")
    List<Produto> buscarAtivo(Long codigo, String nome);

    Produto findByCodigo(Long codigoProduto);

    @Query("SELECT pro.quantidadeEstoque " +
            " FROM Produto pro " +
            "WHERE 1=1 " +
            "  AND pro.codigo = :codigoProduto ")
    Integer buscarQuantidadeEstoque(Long codigoProduto);

}
