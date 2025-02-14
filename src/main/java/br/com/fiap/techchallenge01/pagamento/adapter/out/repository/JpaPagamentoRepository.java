package br.com.fiap.techchallenge01.pagamento.adapter.out.repository;

import br.com.fiap.techchallenge01.pagamento.adapter.out.entity.JpaPagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPagamentoRepository extends JpaRepository<JpaPagamentoEntity, UUID> {

    Optional<JpaPagamentoEntity> findByCodigoPedido(String idPedido);
}
