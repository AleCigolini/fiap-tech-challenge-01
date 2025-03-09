package br.com.fiap.techchallenge01.cliente.adapter.in.controller;

import br.com.fiap.techchallenge01.cliente.adapter.in.port.ClienteInputPort;
import br.com.fiap.techchallenge01.cliente.adapter.in.presenter.ClientePresenter;
import br.com.fiap.techchallenge01.cliente.application.usecase.ClienteUseCase;
import br.com.fiap.techchallenge01.cliente.adapter.in.mapper.ClienteMapper;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteInputPort {
    private final ClienteMapper clienteMapper;
    private final ClientePresenter clientePresenter;

    private final ClienteUseCase clienteUseCase;

    @Override
    @GetMapping("/cpf")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorCpf(@RequestParam String cpf) {
        return clientePresenter.toResponse(clienteUseCase.buscarClientePorCpf(new Cpf(cpf)));
    }

    @Override
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorEmail(@RequestParam String email) {
        return clientePresenter.toResponse(clienteUseCase.buscarClientePorEmail(email));
    }

    @Override
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorId(@RequestParam UUID id) {
        return  clientePresenter.toResponse(clienteUseCase.buscarClientePorId(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto cadastrarCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        return clientePresenter.toResponse(clienteUseCase.salvarCliente(clienteMapper.requestDtoToDomain(clienteRequestDto)));
    }

}
