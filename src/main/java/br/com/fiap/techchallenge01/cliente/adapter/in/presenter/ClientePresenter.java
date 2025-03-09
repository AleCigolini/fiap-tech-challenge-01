package br.com.fiap.techchallenge01.cliente.adapter.in.presenter;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientePresenter {
    private ModelMapper modelMapper;

    public ClienteResponseDto toResponse(Cliente usuario) {
        return modelMapper.map(usuario, ClienteResponseDto.class);
    }
}
