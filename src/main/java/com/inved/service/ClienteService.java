package com.inved.service;

import com.inved.domain.pedido.Cliente;
import com.inved.exception.BadRequestException;
import com.inved.exception.InternalServerErrorException;

public interface ClienteService {

    Cliente buscarPeloCodigo(Long codigoCliente) throws BadRequestException, InternalServerErrorException;

}
