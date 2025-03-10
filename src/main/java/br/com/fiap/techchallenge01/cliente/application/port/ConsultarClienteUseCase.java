package br.com.fiap.techchallenge01.cliente.application.port;

import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.core.utils.domain.Email;

import java.util.UUID;

public interface ConsultarClienteUseCase {
    Cliente buscarClientePorCpf(Cpf cpf);
    Cliente buscarClientePorId(UUID id);
    Cliente buscarClientePorEmail(Email email);
}
