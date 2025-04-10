package com.inved.service.impl;

import com.inved.domain.pedido.Cliente;
import com.inved.exception.BadRequestException;
import com.inved.exception.InternalServerErrorException;
import com.inved.repository.ClienteRepository;
import com.inved.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscarPeloCodigo(Long codigoCliente) throws BadRequestException, InternalServerErrorException {
        try {
            if (codigoCliente == null) {
                throw new BadRequestException("Código do cliente não encontrado para buscar o cliente!");
            }
            final Cliente clienteEncontrado = clienteRepository.findByCodigo(codigoCliente);
            if (clienteEncontrado == null) {
                throw new BadRequestException("Cliente: " + codigoCliente + " não encontrado cadastro!");
            }
            return clienteEncontrado;
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar o cliente pelo código: "+ codigoCliente + "! - " + e.getMessage());
        }
    }

}
