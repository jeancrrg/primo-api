package com.primo.service.impl;

import com.primo.config.websocket.PrestadorWebSocketHandler;
import com.primo.domain.cadastro.SolicitacaoServico;
import com.primo.domain.enums.StatusSolicitacao;
import com.primo.dto.PrestadorServicoDTO;
import com.primo.dto.request.SolicitacaoServicoRequest;
import com.primo.exception.BadRequestException;
import com.primo.exception.InternalServerErrorException;
import com.primo.repository.SolicitacaoServicoRepository;
import com.primo.service.PrestadorServicoService;
import com.primo.service.SolicitacaoServicoService;
import com.primo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SolicitacaoServicoServiceImpl implements SolicitacaoServicoService {

    private final SolicitacaoServicoRepository solicitacaoServicoRepository;
    private final PrestadorWebSocketHandler prestadorWebSocketHandler;
    private final ValidationUtil validationUtil;
    private final PrestadorServicoService prestadorServicoService;

    public SolicitacaoServicoServiceImpl(SolicitacaoServicoRepository solicitacaoServicoRepository,
                                         PrestadorWebSocketHandler prestadorWebSocketHandler,
                                         ValidationUtil validationUtil,
                                         PrestadorServicoService prestadorServicoService) {
        this.solicitacaoServicoRepository = solicitacaoServicoRepository;
        this.prestadorWebSocketHandler = prestadorWebSocketHandler;
        this.validationUtil = validationUtil;
        this.prestadorServicoService = prestadorServicoService;
    }

    public void enviarSolicitacao(SolicitacaoServicoRequest solicitacaoServicoRequest) {
        try {
            validarAntesEnviarSolicitacao(solicitacaoServicoRequest);

            final var solicitacaoServico = SolicitacaoServico.builder()
                            .codigoCliente(solicitacaoServicoRequest.codigoCliente())
                            .codigoPrestador(solicitacaoServicoRequest.codigoPrestador())
                            .data(LocalDateTime.now())
                            .status(StatusSolicitacao.PENDENTE)
                            .build();

            //solicitacaoServicoRepository.save(solicitacaoServico);
            prestadorWebSocketHandler.enviarSolicitacao(solicitacaoServicoRequest.codigoPrestador(), "");
        } catch (BadRequestException e) {
            throw new BadRequestException("Falha ao validar antes de enviar a solicitação! - " + e.getMessage(), this);
        } catch (Exception e) {
            throw new InternalServerErrorException("Erro ao enviar a solicitação!",
                    "Código cliente: " + solicitacaoServicoRequest.codigoCliente() + " - Código prestador: " + solicitacaoServicoRequest.codigoPrestador(),
                    e.getMessage(), this);
        }
    }

    private void validarAntesEnviarSolicitacao(SolicitacaoServicoRequest solicitacaoServicoRequest) throws BadRequestException {
        validarCamposObrigatoriosSolicitacao(solicitacaoServicoRequest);
        final PrestadorServicoDTO prestadorServicoDTO = prestadorServicoService.buscarPeloCodigo(solicitacaoServicoRequest.codigoPrestador());
        if (prestadorServicoDTO == null) {
            throw new BadRequestException("Nenhum prestador encontrado!");
        }
    }

    private void validarCamposObrigatoriosSolicitacao(SolicitacaoServicoRequest solicitacaoServicoRequest) throws BadRequestException {
        validationUtil.validarCampoVazio(solicitacaoServicoRequest, "Solicitação");
        validationUtil.validarCampoVazio(solicitacaoServicoRequest.codigoCliente(), "Código do cliente");
        validationUtil.validarCampoVazio(solicitacaoServicoRequest.codigoPrestador(), "Código do prestador");
    }

}
