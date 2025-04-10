package com.inved.service.impl;

import com.inved.domain.cadastro.ImagemProduto;
import com.inved.exception.ArquivoAmazonException;
import com.inved.exception.BadRequestException;
import com.inved.exception.ConverterException;
import com.inved.exception.InternalServerErrorException;
import com.inved.repository.ImagemProdutoRepository;
import com.inved.service.ArquivoAmazonService;
import com.inved.service.ImagemProdutoService;
import com.inved.util.FormatterUtil;
import com.inved.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagemProdutoServiceImpl implements ImagemProdutoService {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;

    @Autowired
    private ArquivoAmazonService arquivoAmazonService;

    @Autowired
    private LoggerUtil loggerUtil;

    @Autowired
    private FormatterUtil formatterUtil;

    private final String DIRETORIO_IMAGEM = "imagens/produtos";

    public List<ImagemProduto> buscar(Long codigo, String nome, Long codigoProduto) throws ConverterException, ArquivoAmazonException, InternalServerErrorException {
        try {
            if (nome != null && !nome.isEmpty()) {
                final String nomeFormatado = formatterUtil.removerAcentos(nome);
                nome = "%" + nomeFormatado.toUpperCase().trim() + "%";
            }
            List<ImagemProduto> listaImagensProdutos = imagemProdutoRepository.buscar(codigo, nome, codigoProduto);
            if (listaImagensProdutos != null && !listaImagensProdutos.isEmpty()) {
                listaImagensProdutos = processarImagensProdutoAmazon(listaImagensProdutos);
            }
            return listaImagensProdutos;
        } catch (ConverterException e) {
            throw new ConverterException("Erro ao converter o arquivo para buscar as imagens do produto! - " + e.getMessage());
        } catch (ArquivoAmazonException e) {
            throw new ArquivoAmazonException("Erro ao buscar as imagens do produto no repositório da amazon! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar as imagens do produto! - " + e.getMessage());
        }
    }

    public List<ImagemProduto> processarImagensProdutoAmazon(List<ImagemProduto> listaImagensProduto) throws ConverterException,
                                                                                                                ArquivoAmazonException {
        final List<ImagemProduto> listaImagensProdutoAmazon = new ArrayList<>();
        for (ImagemProduto imagemProduto : listaImagensProduto) {
            imagemProduto.setUrlImagem(arquivoAmazonService.buscarUrlArquivoAmazon(DIRETORIO_IMAGEM + "/"
                                        + imagemProduto.getCodigoProduto() + "/" + imagemProduto.getNomeImagemServidor()));
            listaImagensProdutoAmazon.add(imagemProduto);
        }
        return listaImagensProdutoAmazon;
    }

    public List<String> buscarUrlImagensProduto(Long codigoProduto) throws BadRequestException, ConverterException,
                                                                            ArquivoAmazonException, InternalServerErrorException {
        if (codigoProduto == null) {
            throw new BadRequestException("Código do produto não informado!");
        }
        final List<ImagemProduto> listaImagensProduto = buscar(null, null, codigoProduto);
        final List<String> listaUrlImagensProduto = new ArrayList<>();
        for (ImagemProduto imagemProduto : listaImagensProduto) {
            listaUrlImagensProduto.add(imagemProduto.getUrlImagem());
        }
        return listaUrlImagensProduto;
    }

}
