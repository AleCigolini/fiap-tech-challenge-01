package br.com.fiap.techchallenge01.cliente.application.service;

import br.com.fiap.techchallenge01.cliente.adapter.out.port.ClienteOutputPort;
import br.com.fiap.techchallenge01.cliente.application.exception.ClienteNaoEncontradoException;
import br.com.fiap.techchallenge01.cliente.application.exception.ClienteValidacaoException;
import br.com.fiap.techchallenge01.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
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

    @Override
    public Cliente buscarClientePorCpf(Cpf cpf) {
        List<Cliente> usuariosEncontradosPorCpf = clienteOutputPort.buscarClientePorCpf(cpf);
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorCpf, "cpf", cpf.getValue());
        return usuariosEncontradosPorCpf.getFirst();
    }

    @Override
    public Cliente buscarClientePorId(UUID id) {
        Cliente cliente = clienteOutputPort.buscarClientePorId(id)
                .orElse(null);
        if (cliente == null) {
            this.throwClienteNaoEncontradoException("id", id.toString());
        }
        return cliente;
    }

    @Override
    public Cliente buscarClientePorEmail(Email email) {
        List<Cliente> usuariosEncontradosPorEmail = clienteOutputPort.buscarClientePorEmail(email);
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorEmail, "email", email.getEndereco());
        return usuariosEncontradosPorEmail.getFirst();
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
