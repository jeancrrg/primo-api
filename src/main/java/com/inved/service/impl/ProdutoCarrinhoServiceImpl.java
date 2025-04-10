package com.inved.service.impl;

import com.inved.domain.builder.ProdutoCarrinhoBuilder;
import com.inved.domain.cadastro.Produto;
import com.inved.domain.dto.ProdutoCarrinhoDTO;
import com.inved.domain.embeddabledid.ProdutoCarrinhoId;
import com.inved.domain.pedido.Cliente;
import com.inved.domain.pedido.ProdutoCarrinho;
import com.inved.exception.BadRequestException;
import com.inved.exception.InternalServerErrorException;
import com.inved.repository.ProdutoCarrinhoRepository;
import com.inved.service.ClienteService;
import com.inved.service.ImagemProdutoService;
import com.inved.service.ProdutoCarrinhoService;
import com.inved.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoCarrinhoServiceImpl implements ProdutoCarrinhoService {

    private final ProdutoCarrinhoRepository produtoCarrinhoRepository;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final ImagemProdutoService imagemProdutoService;

    public ProdutoCarrinhoServiceImpl(ProdutoCarrinhoRepository produtoCarrinhoRepository,
                                      ProdutoService produtoService,
                                      ClienteService clienteService,
                                      ImagemProdutoService imagemProdutoService) {
        this.produtoCarrinhoRepository = produtoCarrinhoRepository;
        this.produtoService = produtoService;
        this.clienteService = clienteService;
        this.imagemProdutoService = imagemProdutoService;
    }

    public List<ProdutoCarrinhoDTO> buscar(Long codigoCliente) throws BadRequestException, InternalServerErrorException {
        try {
            final List<ProdutoCarrinhoDTO> listaProdutosCarrinhoDTO = new ArrayList<>();
            if (codigoCliente == null) {
                throw new BadRequestException("Código do cliente não informado para buscar os produtos do carrinho!");
            }
            final List<ProdutoCarrinho> listaProdutosCarrinho = produtoCarrinhoRepository.buscarPeloCodigoCliente(codigoCliente);
            if (listaProdutosCarrinho != null && !listaProdutosCarrinho.isEmpty()) {
                for (ProdutoCarrinho produtoCarrinho : listaProdutosCarrinho) {
                    final List<String> listaUrlImagensProduto = imagemProdutoService.buscarUrlImagensProduto(produtoCarrinho.getProdutoCarrinhoId().getProduto().getCodigo());
                    final ProdutoCarrinhoDTO produtoCarrinhoDTO = new ProdutoCarrinhoDTO().toProdutoCarrinhoDTO(produtoCarrinho, listaUrlImagensProduto);
                    listaProdutosCarrinhoDTO.add(produtoCarrinhoDTO);
                }
            }
            return listaProdutosCarrinhoDTO;
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os produtos no carrinho! - " + e.getMessage());
        }
    }

    public List<Long> buscarCodigoProdutos(Long codigoCliente) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoCliente == null) {
                throw new BadRequestException("Código do cliente não informado para buscar o código de produtos do carrinho!");
            }
            final List<ProdutoCarrinho> listaProdutosCarrinhoDTO = produtoCarrinhoRepository.buscarPeloCodigoCliente(codigoCliente);
            List<Long> listaCodigoProdutos = new ArrayList<>();
            if (listaProdutosCarrinhoDTO != null && !listaProdutosCarrinhoDTO.isEmpty()) {
                listaCodigoProdutos = listaProdutosCarrinhoDTO.stream().map(produtoCarrinho -> produtoCarrinho.getProdutoCarrinhoId().getProduto().getCodigo()).collect(Collectors.toList());
            }
            return listaCodigoProdutos;
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o código de produtos no carrinho! - " + e.getMessage());
        }
    }

    public void atualizar(Long codigoCliente, List<ProdutoCarrinhoDTO> listaProdutosCarrinhoDTO) throws BadRequestException, InternalServerErrorException {
        try {
            if (listaProdutosCarrinhoDTO != null && !listaProdutosCarrinhoDTO.isEmpty()) {
                final Cliente clienteEncontrado = clienteService.buscarPeloCodigo(codigoCliente);

                for (ProdutoCarrinhoDTO produtoCarrinhoDTO : listaProdutosCarrinhoDTO) {
                    final Produto produtoEncontrado = produtoService.buscarPeloCodigo(produtoCarrinhoDTO.getCodigo());

                    final ProdutoCarrinho produtoCarrinho = ProdutoCarrinhoBuilder.builder()
                            .produtoCarrinhoId(new ProdutoCarrinhoId(clienteEncontrado, produtoEncontrado))
                            .quantidadeProduto(produtoCarrinhoDTO.getQuantidadeProduto())
                            .valorSubtotalProduto(produtoEncontrado.getPreco().multiply(new BigDecimal(produtoCarrinhoDTO.getQuantidadeProduto())))
                            .dataUltimaAlteracao(LocalDateTime.now()).build();

                    produtoCarrinhoRepository.save(produtoCarrinho);
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao atualizar o carrinho! - " + e.getMessage());
        }
    }

    public void adicionar(Long codigoProduto, Integer quantidade, Long codigoCliente) throws BadRequestException, InternalServerErrorException {
        try {
            validarAntesAdicionarProdutoCarrinho(codigoProduto, quantidade, codigoCliente);
            final Produto produtoEncontrado = produtoService.buscarPeloCodigo(codigoProduto);
            final Cliente clienteEncontrado = clienteService.buscarPeloCodigo(codigoCliente);

            final ProdutoCarrinho produtoCarrinhoEncontrado = produtoCarrinhoRepository.buscarProdutoCliente(codigoProduto, codigoCliente);
            if (produtoCarrinhoEncontrado != null) {
                quantidade += produtoCarrinhoEncontrado.getQuantidadeProduto();
            }

            final ProdutoCarrinho produtoCarrinho = ProdutoCarrinhoBuilder.builder()
                                                        .produtoCarrinhoId(new ProdutoCarrinhoId(clienteEncontrado, produtoEncontrado))
                                                        .quantidadeProduto(quantidade)
                                                        .valorSubtotalProduto(produtoEncontrado.getPreco().multiply(new BigDecimal(quantidade)))
                                                        .dataUltimaAlteracao(LocalDateTime.now()).build();

            produtoCarrinhoRepository.save(produtoCarrinho);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao adicionar o produto no carrinho! - " + e.getMessage());
        }
    }

    public void validarAntesAdicionarProdutoCarrinho(Long codigoProduto, Integer quantidade, Long codigoCliente) throws BadRequestException {
        if (codigoProduto == null) {
            throw new BadRequestException("Código do produto não informado para adicionar o produto ao carrinho!");
        }
        if (quantidade == null) {
            throw new BadRequestException("Quantidade do produto não informado para adicionar o produto ao carrinho!");
        }
        if (codigoCliente == null) {
            throw new BadRequestException("Código do cliente não informado para adicionar o produto ao carrinho!");
        }
    }

    @Transactional
    public void remover(Long codigoProduto, Long codigoCliente) throws BadRequestException, InternalServerErrorException {
        try {
            validarAntesRemoverProdutoCarrinho(codigoProduto, codigoCliente);
            produtoCarrinhoRepository.excluirProdutoCarrinho(codigoProduto, codigoCliente);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao remover o produto do carrinho! - " + e.getMessage());
        }
    }

    public void validarAntesRemoverProdutoCarrinho(Long codigoProduto, Long codigoCliente) throws BadRequestException {
        if (codigoProduto == null) {
            throw new BadRequestException("Código do produto não informado para remover o produto do carrinho!");
        }
        if (codigoCliente == null) {
            throw new BadRequestException("Código do cliente não informado para remover o produto do carrinho!");
        }
    }
}
