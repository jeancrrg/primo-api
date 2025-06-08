package com.primo.service;

import com.primo.dto.request.SolicitacaoServicoRequest;

public interface SolicitacaoServicoService {

    void enviarSolicitacao(SolicitacaoServicoRequest solicitacaoServicoRequest);

}
