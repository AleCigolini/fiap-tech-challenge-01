package br.com.fiap.techchallenge01.cliente.adapter.in.controller;

import br.com.fiap.techchallenge01.cliente.adapter.in.controller.api.ClienteApi;
import br.com.fiap.techchallenge01.cliente.application.service.ClienteService;
import br.com.fiap.techchallenge01.cliente.config.mapper.ClienteMapper;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController implements ClienteApi {
    private ClienteMapper clienteMapper;
    private ClienteService clienteService;

    @Override
    @GetMapping("/cpf")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorCpf(@RequestParam String cpf) {
        return clienteMapper.toResponse(clienteService.buscarClientePorCpf(cpf));
    }

    @Override
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorEmail(@RequestParam String email) {
        return clienteMapper.toResponse(clienteService.buscarClientePorEmail(email));
    }

    @Override
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto buscarClientePorId(@RequestParam UUID id) {
        return  clienteMapper.toResponse(clienteService.buscarClientePorId(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponseDto cadastrarCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        return clienteMapper.toResponse(clienteService.salvarCliente(clienteMapper.requestDtoToDomain(clienteRequestDto)));
    }

}
