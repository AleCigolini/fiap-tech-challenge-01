package br.com.fiap.techchallenge01.cliente.application.service;

import br.com.fiap.techchallenge01.cliente.application.exception.ClienteNaoEncontradoException;
import br.com.fiap.techchallenge01.cliente.application.exception.ClienteValidacaoException;
import br.com.fiap.techchallenge01.cliente.application.usecase.ClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.adapter.out.port.ClienteOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ClienteService implements ClienteUseCase {
    private ClienteOutputPort clienteOutputPort;

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        this.validarClienteExistente(cliente);
        return clienteOutputPort.salvarCliente(cliente);
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        List<Cliente> usuariosEncontradosPorCpf = clienteOutputPort.buscarClientePorCpf(cpf);
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorCpf, "cpf", cpf);
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
    public Cliente buscarClientePorEmail(String email) {
        List<Cliente> usuariosEncontradosPorEmail = clienteOutputPort.buscarClientePorEmail(email);
        validarListaClienteUnicoEncontrado(usuariosEncontradosPorEmail, "email", email);
        return usuariosEncontradosPorEmail.getFirst();
    }

    private void throwClienteNaoEncontradoException(String campoBusca, String valorBusca) {
        throw new ClienteNaoEncontradoException(String.format("Não foi encontrado nenhum cliente para o %s: %s", campoBusca, valorBusca));
    }

    private void validarListaClienteUnicoEncontrado(List<Cliente> clientes, String campoBusca, String valorBusca) {
        if (clientes.isEmpty()) {
            this.throwClienteNaoEncontradoException(campoBusca, valorBusca);
        }
        if (clientes.size() > 1) {
            throw new ClienteValidacaoException(String.format("Encontrado mais de um cliente para o %s: %s", campoBusca, valorBusca));
        }
    }

    public void validarClienteExistente(Cliente cliente) {
        List<String> erros = new ArrayList<>();

        validarDuplicidade(cliente.getCpf().getValue(), clienteOutputPort::buscarClientePorCpf, "Já existe um cliente cadastrado com o CPF informado.", erros);
        validarDuplicidade(cliente.getEmail(), clienteOutputPort::buscarClientePorEmail, "Já existe um cliente cadastrado com o e-mail informado.", erros);

        if (!erros.isEmpty()) {
            throw new ClienteValidacaoException(String.join(", ", erros));
        }
    }

    private void validarDuplicidade(String campo, Function<String, List<Cliente>>busca, String mensagemErro, List<String> erros) {
        if (campo != null) {
            List<Cliente> clienteEncontrados = busca.apply(campo);
            if (!clienteEncontrados.isEmpty()) {
                erros.add(mensagemErro);
            }
        }
    }
}
