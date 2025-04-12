package com.primo.service.impl;

import com.primo.domain.cadastro.dto.EnderecoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.EnderecoRepository;
import com.primo.service.EnderecoService;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public EnderecoDTO buscarPeloCodigoPessoa(Long codigoPessoa) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoPessoa == null) {
                throw new BadRequestException("Código não informado!");
            }
            return enderecoRepository.buscarPeloCodigoPessoa(codigoPessoa);
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de buscar o endereço! - " + e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o endereço! - " + e.getMessage());
        }
    }

}
