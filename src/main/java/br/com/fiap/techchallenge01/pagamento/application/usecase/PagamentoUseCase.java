package br.com.fiap.techchallenge01.pagamento.application.usecase;

import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;

public interface PagamentoUseCase {

    Pagamento enviarPagamento(Pagamento pagamento);

    PagamentoResponseDTO verificarPagamentoPedido(String idPedido);
}