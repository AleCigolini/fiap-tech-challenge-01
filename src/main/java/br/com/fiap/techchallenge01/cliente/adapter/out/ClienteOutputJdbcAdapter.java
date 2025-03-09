package br.com.fiap.techchallenge01.cliente.adapter.out;

import br.com.fiap.techchallenge01.cliente.adapter.out.jpa.JpaClienteRepository;
import br.com.fiap.techchallenge01.cliente.adapter.out.jpa.entity.JpaClienteEntity;
import br.com.fiap.techchallenge01.cliente.adapter.out.mapper.JpaClienteMapper;
import br.com.fiap.techchallenge01.cliente.adapter.out.port.ClienteOutputPort;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ClienteOutputJdbcAdapter implements ClienteOutputPort {
    private JpaClienteRepository jpaClienteRepository;
    private JpaClienteMapper modelMapper;

    @Override
    public Cliente salvarCliente(Cliente usuario) {
        JpaClienteEntity jpaClienteEntity = modelMapper.toJpaClienteEntity(usuario);
        return modelMapper.toCliente(jpaClienteRepository.save(jpaClienteEntity));
    }

    @Override
    public List<Cliente> buscarClientePorCpf(String cpf) {
        return jpaClienteRepository.findByCpf(cpf)
                .stream()
                .map(jpaClienteEntity -> modelMapper.toCliente(jpaClienteEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> buscarClientePorId(UUID id) {
        return jpaClienteRepository.findById(id)
                .map(jpaClienteEntity -> modelMapper.toCliente(jpaClienteEntity));
    }

    @Override
    public List<Cliente> buscarClientePorEmail(String email) {
        return jpaClienteRepository.findByEmail(email)
                .stream()
                .map(jpaClienteEntity -> modelMapper.toCliente(jpaClienteEntity))
                .collect(Collectors.toList());
    }

}
