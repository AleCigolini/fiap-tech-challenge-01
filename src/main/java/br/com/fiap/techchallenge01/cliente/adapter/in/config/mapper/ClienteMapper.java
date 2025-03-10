package br.com.fiap.techchallenge01.cliente.adapter.in.config.mapper;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;


public interface ClienteMapper {
    Cliente requestDtoToDomain(ClienteRequestDto clienteRequestDto);
}
