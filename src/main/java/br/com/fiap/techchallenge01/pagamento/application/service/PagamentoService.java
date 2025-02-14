package br.com.fiap.techchallenge01.pagamento.application.service;

import br.com.fiap.techchallenge01.pagamento.application.exception.PagamentoNaoEncontradoException;
import br.com.fiap.techchallenge01.pagamento.application.usecase.PagamentoUseCase;
import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge01.pagamento.domain.repository.PagamentoRepository;
import br.com.fiap.techchallenge01.pagamento.mapper.PagamentoMapper;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService implements PagamentoUseCase {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Override
    public Pagamento enviarPagamento(Pagamento pagamento) {
        return pagamentoRepository.enviarPagamento(pagamento);
    }

    @Override
    @Transactional(readOnly = true)
    public PagamentoResponseDTO verificarPagamentoPedido(String idPedido) {
        Pagamento pagamento = pagamentoRepository.buscarPagamentoPorPedidoId(idPedido).orElseThrow(() -> new PagamentoNaoEncontradoException(idPedido));

        return pagamentoMapper.toResponse(pagamento);
    }
}