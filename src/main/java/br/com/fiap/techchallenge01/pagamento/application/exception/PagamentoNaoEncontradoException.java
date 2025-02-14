package br.com.fiap.techchallenge01.pagamento.application.exception;

import br.com.fiap.techchallenge01.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class PagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PagamentoNaoEncontradoException(Long idPedido) {
        this(STR."Não existe um pagamento com pedido ID: \{idPedido}");
    }

}
