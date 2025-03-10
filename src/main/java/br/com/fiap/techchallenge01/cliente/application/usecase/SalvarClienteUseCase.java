package br.com.fiap.techchallenge01.cliente.application.usecase;

import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;

public interface SalvarClienteUseCase {
    ClienteResponseDto salvarCliente(ClienteRequestDto clienteRequestDto);
}
