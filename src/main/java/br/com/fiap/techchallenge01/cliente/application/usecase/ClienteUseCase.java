package br.com.fiap.techchallenge01.cliente.application.usecase;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;

import java.util.UUID;

public interface ClienteUseCase {
    Cliente salvarCliente(Cliente cliente);
    Cliente buscarClientePorCpf(Cpf cpf);
    Cliente buscarClientePorId(UUID id);
    Cliente buscarClientePorEmail(String email);
}
