package br.com.fiap.techchallenge01.pagamento.adapter.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class JpaPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "cdPedido")
    private String codigoPedido;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime dataCriacao;
}