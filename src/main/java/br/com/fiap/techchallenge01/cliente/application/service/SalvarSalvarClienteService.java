package br.com.fiap.techchallenge01.cliente.application.service;

import br.com.fiap.techchallenge01.cliente.application.exception.ClienteValidacaoException;
import br.com.fiap.techchallenge01.cliente.application.usecase.SalvarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.cliente.adapter.out.port.ClienteOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class SalvarSalvarClienteService implements SalvarClienteUseCase {
    private ClienteOutputPort clienteOutputPort;

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        this.validarClienteExistente(cliente);
        return clienteOutputPort.salvarCliente(cliente);
    }

    public void validarClienteExistente(Cliente cliente) {
        List<String> erros = new ArrayList<>();

        validarDuplicidade(cliente.getCpf(), clienteOutputPort::buscarClientePorCpf, "Já existe um cliente cadastrado com o CPF informado.", erros);
        validarDuplicidade(cliente.getEmail(), clienteOutputPort::buscarClientePorEmail, "Já existe um cliente cadastrado com o e-mail informado.", erros);

        if (!erros.isEmpty()) {
            throw new ClienteValidacaoException(String.join(", ", erros));
        }
    }

    private <T> void validarDuplicidade(T campo, Function<T, List<Cliente>>busca, String mensagemErro, List<String> erros) {
        if (campo != null) {
            List<Cliente> clienteEncontrados = busca.apply(campo);
            if (!clienteEncontrados.isEmpty()) {
                erros.add(mensagemErro);
            }
        }
    }
}
