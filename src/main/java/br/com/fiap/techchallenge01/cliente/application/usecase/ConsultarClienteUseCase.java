package br.com.fiap.techchallenge01.cliente.application.usecase;

import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;

import java.util.UUID;

public interface ConsultarClienteUseCase {
    ClienteResponseDto buscarClientePorCpf(String cpf);
    ClienteResponseDto buscarClientePorId(UUID id);
    ClienteResponseDto buscarClientePorEmail(String email);
}
