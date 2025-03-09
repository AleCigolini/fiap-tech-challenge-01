package br.com.fiap.techchallenge01.cliente.adapter.out.mapper;

import br.com.fiap.techchallenge01.cliente.adapter.out.jpa.entity.JpaClienteEntity;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JpaClienteMapper {
    private ModelMapper modelMapper;

    public JpaClienteEntity toJpaClienteEntity(Cliente usuario) {
        return modelMapper.map(usuario, JpaClienteEntity.class);
    }

    public Cliente toCliente(JpaClienteEntity jpaClienteEntity) {
        return modelMapper.map(jpaClienteEntity, Cliente.class);
    }
}