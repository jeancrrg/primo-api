package com.primo.service.impl;

import com.primo.domain.dto.PrestadorServicoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.PrestadorServicoRepository;
import com.primo.service.EnderecoService;
import com.primo.service.PrestadorServicoService;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestadorServicoServiceImpl implements PrestadorServicoService {

    private final PrestadorServicoRepository prestadorServicoRepository;
    private final EnderecoService enderecoService;
    private final ValidationUtil validationUtil;

    public PrestadorServicoServiceImpl(PrestadorServicoRepository prestadorServicoRepository, EnderecoService enderecoService, ValidationUtil validationUtil) {
        this.prestadorServicoRepository = prestadorServicoRepository;
        this.enderecoService = enderecoService;
        this.validationUtil = validationUtil;
    }

    public List<PrestadorServicoDTO> buscar(String termoBusca) throws BadRequestException, InternalServerErrorException {
        List<PrestadorServicoDTO> listaPrestadoresServico = prestadorServicoRepository.buscar(termoBusca);
        if (!validationUtil.isEmptyList(listaPrestadoresServico)) {
            for (PrestadorServicoDTO prestadorServicoDTO : listaPrestadoresServico) {
                prestadorServicoDTO.setEndereco(enderecoService.buscarPeloCodigoPessoa(prestadorServicoDTO.getCodigo()));
            }
        }
        return listaPrestadoresServico;
    }

}
