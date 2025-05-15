package com.primo.service.impl;

import com.primo.dto.response.AvatarResponse;
import com.primo.exception.InternalServerErrorException;
import com.primo.exception.NotFoundException;
import com.primo.repository.AvatarRepository;
import com.primo.service.AvatarService;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final ValidationUtil validationUtil;

    public AvatarServiceImpl(AvatarRepository avatarRepository, ValidationUtil validationUtil) {
        this.avatarRepository = avatarRepository;
        this.validationUtil = validationUtil;
    }

    public List<AvatarResponse> buscar(Integer codigo) {
        try {
            final List<AvatarResponse> listaAvatares = avatarRepository.buscar(codigo);
            if (validationUtil.isEmptyList(listaAvatares)) {
                throw new NotFoundException("Nenhum avatar encontrado!", this);
            }
            return listaAvatares;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao buscar os avatares!", "Código: " + codigo, e.getMessage(), this);
        }
    }

}
