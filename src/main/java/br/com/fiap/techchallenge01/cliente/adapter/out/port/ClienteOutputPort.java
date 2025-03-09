package br.com.fiap.techchallenge01.cliente.adapter.out.port;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteOutputPort {
    Cliente salvarCliente(Cliente cliente);
    List<Cliente> buscarClientePorCpf(String cpf);
    Optional<Cliente> buscarClientePorId(UUID id);
    List<Cliente> buscarClientePorEmail(String email);
}
