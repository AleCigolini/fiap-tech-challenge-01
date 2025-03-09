package br.com.fiap.techchallenge01.cliente.adapter.out.jpa;


import br.com.fiap.techchallenge01.cliente.adapter.out.jpa.entity.JpaClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaClienteRepository extends JpaRepository<JpaClienteEntity, UUID> {
    List<JpaClienteEntity> findByCpf(String cpf);
    List<JpaClienteEntity> findByEmail(String email);
}
