package br.com.fiap.techchallenge01.cliente.adapter.in.controller;

import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;

import java.util.UUID;

public interface ClienteController {
    ClienteResponseDto buscarClientePorCpf(String cpf);
    ClienteResponseDto buscarClientePorEmail(String email);
    ClienteResponseDto buscarClientePorId(UUID id);
    ClienteResponseDto cadastrarCliente(ClienteRequestDto clienteRequestDto);
}
