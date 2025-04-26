package com.primo.service;

import com.primo.domain.dto.PrestadorServicoDTO;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;

import java.util.List;

public interface PrestadorServicoService {

    List<PrestadorServicoDTO> buscar(String termoPesquisa) throws BadRequestException, InternalServerErrorException;

}
