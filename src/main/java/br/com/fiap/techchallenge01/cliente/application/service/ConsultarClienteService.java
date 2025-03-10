package br.com.fiap.techchallenge01.cliente.application.service;

import br.com.fiap.techchallenge01.cliente.adapter.in.config.presenter.ClientePresenter;
import br.com.fiap.techchallenge01.cliente.adapter.out.port.ClienteOutputPort;
import br.com.fiap.techchallenge01.cliente.application.exception.ClienteNaoEncontradoException;
import br.com.fiap.techchallenge01.cliente.application.exception.ClienteValidacaoException;
import br.com.fiap.techchallenge01.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.domain.dto.response.ClienteResponseDto;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.core.utils.domain.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsultarClienteService implements ConsultarClienteUseCase {
    private ClienteOutputPort clienteOutputPort;
    private ClientePresenter clientePresenter;

    @Override
    public ClienteResponseDto buscarClientePorCpf(String cpf) {
        List<Cliente> usuariosEncontradosPorCpf = clienteOutputPort.buscarClientePorCpf(new Cpf(cpf));
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorCpf, "cpf", cpf);
        return clientePresenter.toResponse(usuariosEncontradosPorCpf.getFirst());
    }

    @Override
    public ClienteResponseDto buscarClientePorId(UUID id) {
        Cliente cliente = clienteOutputPort.buscarClientePorId(id)
                .orElse(null);
        if (cliente == null) {
            this.throwClienteNaoEncontradoException("id", id.toString());
        }
        return clientePresenter.toResponse(cliente);
    }

    @Override
    public ClienteResponseDto buscarClientePorEmail(String email) {
        List<Cliente> usuariosEncontradosPorEmail = clienteOutputPort.buscarClientePorEmail(new Email(email));
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorEmail, "email", email);
        return clientePresenter.toResponse(usuariosEncontradosPorEmail.getFirst());
    }

    private void validarListaClienteUnicoEncontrado(List<Cliente> clientes, String campoBusca, String valorBusca) {
        if (clientes.isEmpty()) {
            this.throwClienteNaoEncontradoException(campoBusca, valorBusca);
        }
        if (clientes.size() > 1) {
            throw new ClienteValidacaoException(String.format("Encontrado mais de um cliente para o %s: %s", campoBusca, valorBusca));
        }
    }

    private void throwClienteNaoEncontradoException(String campoBusca, String valorBusca) {
        throw new ClienteNaoEncontradoException(String.format("Não foi encontrado nenhum cliente para o %s: %s", campoBusca, valorBusca));
    }
}
