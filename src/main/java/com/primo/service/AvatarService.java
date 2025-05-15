package com.primo.service;

import com.primo.dto.response.AvatarResponse;

import java.util.List;

public interface AvatarService {

    List<AvatarResponse> buscar(Integer codigo);

}
