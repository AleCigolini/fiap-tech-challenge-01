package br.com.fiap.techchallenge01.cliente.adapter.in.controller.impl;

import br.com.fiap.techchallenge01.cliente.adapter.in.config.mapper.ClienteMapper;
import br.com.fiap.techchallenge01.cliente.adapter.in.config.presenter.ClientePresenter;
import br.com.fiap.techchallenge01.cliente.adapter.in.controller.ClienteController;
import br.com.fiap.techchallenge01.cliente.application.port.ConsultarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.application.port.SalvarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.core.utils.domain.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class ClienteControllerImpl implements ClienteController {
    private final SalvarClienteUseCase salvarClienteUseCase;
    private final ConsultarClienteUseCase consultarClienteUseCase;

    private final ClienteMapper clienteMapper;
    private final ClientePresenter clientePresenter;

    @Override
    public ClienteResponseDto buscarClientePorCpf(String cpf) {
        return clientePresenter.toResponse(consultarClienteUseCase.buscarClientePorCpf(new Cpf(cpf)));
    }

    @Override
    public ClienteResponseDto buscarClientePorEmail(String email) {
        return clientePresenter.toResponse(consultarClienteUseCase.buscarClientePorEmail(new Email(email)));
    }

    @Override
    public ClienteResponseDto buscarClientePorId(UUID id) {
        return clientePresenter.toResponse(consultarClienteUseCase.buscarClientePorId(id));
    }

    @Override
    public ClienteResponseDto cadastrarCliente(ClienteRequestDto clienteRequestDto) {
        return clientePresenter.toResponse(salvarClienteUseCase.salvarCliente(clienteMapper.requestDtoToDomain(clienteRequestDto)));
    }
}
