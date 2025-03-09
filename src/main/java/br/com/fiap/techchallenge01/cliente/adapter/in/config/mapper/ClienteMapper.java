package br.com.fiap.techchallenge01.cliente.adapter.in.config.mapper;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.domain.dto.request.ClienteRequestDto;
import br.com.fiap.techchallenge01.core.config.exception.exceptions.ValidacaoEntidadeException;
import lombok.AllArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClienteMapper {
    private ModelMapper modelMapper;

    public Cliente requestDtoToDomain(ClienteRequestDto clienteRequestDto) {
        try {
            return modelMapper.map(clienteRequestDto, Cliente.class);
        } catch (MappingException e) {
            if (e.getCause() instanceof ValidacaoEntidadeException) {
                throw (ValidacaoEntidadeException) e.getCause();
            }
            throw e;
        }
    }

}
