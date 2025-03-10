package br.com.fiap.techchallenge01.cliente.adapter.in.controller;

import br.com.fiap.techchallenge01.cliente.adapter.in.port.ClienteInputPort;
import br.com.fiap.techchallenge01.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.application.usecase.SalvarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteInputPort {
    private final SalvarClienteUseCase salvarClienteUseCase;
    private final ConsultarClienteUseCase consultarClienteUseCase;

    @Override
    @GetMapping("/cpf")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorCpf(@RequestParam String cpf) {
        return consultarClienteUseCase.buscarClientePorCpf(cpf);
    }

    @Override
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorEmail(@RequestParam String email) {
        return consultarClienteUseCase.buscarClientePorEmail(email);
    }

    @Override
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorId(@RequestParam UUID id) {
        return consultarClienteUseCase.buscarClientePorId(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto cadastrarCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        return salvarClienteUseCase.salvarCliente(clienteRequestDto);
    }

}
