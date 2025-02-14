package br.com.fiap.techchallenge01.pedido.application.exception;

import br.com.fiap.techchallenge01.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long id) {
        this(STR."Não existe um pedido com o ID: \{id}");
    }
}