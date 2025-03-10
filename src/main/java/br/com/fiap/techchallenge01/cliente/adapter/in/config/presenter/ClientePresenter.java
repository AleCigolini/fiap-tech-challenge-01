package br.com.fiap.techchallenge01.cliente.adapter.in.config.presenter;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;

public interface ClientePresenter {
    ClienteResponseDto toResponse(Cliente usuario);
}
