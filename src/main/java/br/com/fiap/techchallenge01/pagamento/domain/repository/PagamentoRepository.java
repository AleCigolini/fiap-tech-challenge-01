package br.com.fiap.techchallenge01.pagamento.domain.repository;

import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;

import java.util.Optional;

public interface PagamentoRepository {

    Pagamento enviarPagamento(Pagamento pagamento);

    Optional<Pagamento> buscarPagamentoPorPedidoId(String idPedido);
}