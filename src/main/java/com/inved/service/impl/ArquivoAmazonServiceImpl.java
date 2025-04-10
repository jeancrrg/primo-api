package com.inved.service.impl;

import com.inved.exception.ArquivoAmazonException;
import com.inved.exception.BadRequestException;
import com.inved.service.ArquivoAmazonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Service
public class ArquivoAmazonServiceImpl implements ArquivoAmazonService {

    @Value("${amazon.bucket}")
    private String BUCKET_AMAZON;

    @Value("${amazon.region}")
    private String REGION_AMAZON;

    public String buscarUrlArquivoAmazon(String caminhoDiretorio) throws ArquivoAmazonException {
        try {
            if (caminhoDiretorio == null || caminhoDiretorio.isBlank()) {
                throw new BadRequestException("Caminho do diretório do arquivo não encontrado para buscar!");
            }
            // Usado recurdo try-with-resources para garantir o autoclose automático do S3Presigner
            try (S3Presigner presigner = S3Presigner.builder().region(Region.of(REGION_AMAZON))
                    .credentialsProvider(ProfileCredentialsProvider.create()).build()) {

                // Requisição de obtenção do objeto
                final GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

                // Criação da URL pré-assinada com expiração de 30 minutos
                final GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(30)).getObjectRequest(getObjectRequest).build();

                // Gera a URL pré-assinada
                final URL presignedUrl = presigner.presignGetObject(presignRequest).url();

                // Retorna a URL
                return presignedUrl.toExternalForm();
            }
        } catch (BadRequestException e) {
            throw new ArquivoAmazonException(e.getMessage());
        } catch (Exception e) {
            throw new ArquivoAmazonException("Erro ao buscar arquivo no diretório: " + caminhoDiretorio + " da amazon! - " + e.getMessage());
        }
    }

}
