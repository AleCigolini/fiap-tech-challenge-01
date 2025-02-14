package br.com.fiap.techchallenge01.pedido.domain.repository;

import br.com.fiap.techchallenge01.pedido.domain.Pedido;
import br.com.fiap.techchallenge01.produto.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    List<Pedido> buscarPedidos();

    Optional<Pedido> buscarPedidoPorId(String id);

    Pedido criarPedido(Pedido pedido);

    Pedido atualizarStatusPedido(Pedido pedido);
}