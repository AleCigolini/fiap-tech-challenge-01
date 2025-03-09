package br.com.fiap.techchallenge01.pedido.adapter.out.entity;

import br.com.fiap.techchallenge01.cliente.adapter.out.jpa.entity.JpaClienteEntity;
import br.com.fiap.techchallenge01.core.utils.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
@EqualsAndHashCode(callSuper = true)
public class JpaPedidoEntity extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "status")
    private String status;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "cd_pagamento")
    private String codigoPagamento;

    @ManyToOne
    @JoinColumn(name="id_cliente", nullable=false)
    private JpaClienteEntity cliente;

    @OneToMany(mappedBy="pedido")
    private List<JpaProdutoPedidoEntity> produtos = new ArrayList<>();
}