package com.primo.service.impl;

import com.primo.dto.EnderecoDTO;
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
                throw new BadRequestException("Código não informado para buscar o endereço!");
            }
            return enderecoRepository.buscarPeloCodigoPessoa(codigoPessoa);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o endereço!", "Código pessoa: " + codigoPessoa, e.getMessage(), this, e);
        }
    }

}
