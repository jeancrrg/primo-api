package com.inved.service.impl;

import com.inved.domain.cadastro.Produto;
import com.inved.domain.dto.ProdutoDTO;
import com.inved.exception.BadRequestException;
import com.inved.exception.InternalServerErrorException;
import com.inved.repository.ProdutoRepository;
import com.inved.service.ImagemProdutoService;
import com.inved.service.ProdutoService;
import com.inved.util.FormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemProdutoService imagemProdutoService;

    @Autowired
    private FormatterUtil formatterUtil;

    public List<ProdutoDTO> buscarAtivo(Long codigo, String nome) throws InternalServerErrorException {
        try {
            final List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
            if (nome != null && !nome.isEmpty()) {
                final String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            final List<Produto> listaProdutos = produtoRepository.buscarAtivo(codigo, nome);
            if (listaProdutos != null && !listaProdutos.isEmpty()) {
                for (Produto produto : listaProdutos) {
                    final List<String> listaUrlImagensProduto = imagemProdutoService.buscarUrlImagensProduto(produto.getCodigo());
                    final ProdutoDTO produtoDTO = new ProdutoDTO().toProdutoDTO(produto, listaUrlImagensProduto);
                    listaProdutosDTO.add(produtoDTO);
                }
            }
            return listaProdutosDTO;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os produtos! - " + e.getMessage());
        }
    }

    public Produto buscarPeloCodigo(Long codigoProduto) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoProduto == null) {
                throw new BadRequestException("Código do produto não encontrado para buscar o produto!");
            }
            final Produto produtoEncontrado = produtoRepository.findByCodigo(codigoProduto);
            if (produtoEncontrado == null) {
                throw new BadRequestException("Produto: " + codigoProduto + " não encontrado cadastro!");
            }
            return produtoEncontrado;
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o produto pelo código: " + codigoProduto + "! - " + e.getMessage());
        }
    }

    public Integer buscarQuantidadeEstoque(Long codigoProduto) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoProduto == null) {
                throw new BadRequestException("Código do produto não informado para buscar a quantidade de estoque!");
            }
            return produtoRepository.buscarQuantidadeEstoque(codigoProduto);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar a quantidade de estoque do produto: " + codigoProduto + "! - " + e.getMessage());
        }
    }

}
