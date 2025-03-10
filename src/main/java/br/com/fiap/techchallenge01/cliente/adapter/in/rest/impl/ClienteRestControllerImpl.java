package br.com.fiap.techchallenge01.cliente.adapter.in.rest.impl;

import br.com.fiap.techchallenge01.cliente.adapter.in.controller.ClienteController;
import br.com.fiap.techchallenge01.cliente.adapter.in.rest.ClienteRestController;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteRestControllerImpl implements ClienteRestController {
    private final ClienteController clienteController;

    @Override
    @GetMapping("/cpf")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorCpf(@RequestParam String cpf) {
        return clienteController.buscarClientePorCpf(cpf);
    }

    @Override
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorEmail(@RequestParam String email) {
        return clienteController.buscarClientePorEmail(email);
    }

    @Override
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorId(@RequestParam UUID id) {
        return clienteController.buscarClientePorId(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto cadastrarCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteController.cadastrarCliente(clienteRequestDto);
    }

}
