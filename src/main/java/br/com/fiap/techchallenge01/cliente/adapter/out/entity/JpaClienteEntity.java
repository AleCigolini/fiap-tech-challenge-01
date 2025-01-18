package br.com.fiap.techchallenge01.cliente.adapter.out.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.fiap.techchallenge01.core.utils.entity.JpaBaseEntity;

import java.util.UUID;

@Data
@Entity
@Table(name = "cliente")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class JpaClienteEntity extends JpaBaseEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String nome;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 11)
    private String cpf;

}
