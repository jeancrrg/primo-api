package com.primo.service.impl;

import com.primo.dto.PrestadorServicoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.exception.NotFoundException;
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

    public PrestadorServicoServiceImpl(PrestadorServicoRepository prestadorServicoRepository,
                                       EnderecoService enderecoService,
                                       ValidationUtil validationUtil) {
        this.prestadorServicoRepository = prestadorServicoRepository;
        this.enderecoService = enderecoService;
        this.validationUtil = validationUtil;
    }

    public List<PrestadorServicoDTO> buscar(String termoPesquisa) {
        try {
            List<PrestadorServicoDTO> listaPrestadoresServico = prestadorServicoRepository.buscar(termoPesquisa);
            if (validationUtil.isEmptyList(listaPrestadoresServico)) {
                throw new NotFoundException("Falha ao buscar os prestadores de serviço! Nenhum prestador de serviço encontrado!", this);
            }
            for (PrestadorServicoDTO prestadorServicoDTO : listaPrestadoresServico) {
                prestadorServicoDTO.setEndereco(enderecoService.buscarPeloCodigoPessoa(prestadorServicoDTO.getCodigo()));
            }
            return listaPrestadoresServico;
        } catch (BadRequestException | NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os prestadores de serviço!", "Termo pesquisa: " + termoPesquisa, e.getMessage(), this);
        }
    }

}
