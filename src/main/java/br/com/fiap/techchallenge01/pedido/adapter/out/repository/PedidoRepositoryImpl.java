package br.com.fiap.techchallenge01.pedido.adapter.out.repository;

import br.com.fiap.techchallenge01.pedido.adapter.out.entity.JpaPedidoEntity;
import br.com.fiap.techchallenge01.pedido.adapter.out.entity.JpaProdutoPedidoEntity;
import br.com.fiap.techchallenge01.pedido.domain.Pedido;
import br.com.fiap.techchallenge01.pedido.domain.ProdutoPedido;
import br.com.fiap.techchallenge01.pedido.domain.repository.PedidoRepository;
import br.com.fiap.techchallenge01.produto.domain.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    @Autowired
    private JpaPedidoRepository jpaPedidoRepository;

    @Autowired
    private JpaProdutoPedidoRepository jpaProdutoPedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Pedido> buscarPedidoPorId(String id) {
        return jpaPedidoRepository.findById(UUID.fromString(id)).map(jpaPedidoRepository -> modelMapper.map(jpaPedidoRepository, Pedido.class));
    }

    @Override
    public List<Pedido> buscarPedidos() {
        return jpaPedidoRepository.findAll()
                .stream()
                .map(jpaPedidoEntity -> modelMapper.map(jpaPedidoEntity, Pedido.class))
                .collect(Collectors.toList());
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {
        JpaPedidoEntity jpaPedidoEntity = modelMapper.map(pedido, JpaPedidoEntity.class);

        JpaPedidoEntity jpaPedidoEntitySalvo = jpaPedidoRepository.save(jpaPedidoEntity);

        for (ProdutoPedido produtoPedido : pedido.getProdutos()) {
            JpaProdutoPedidoEntity jpaProdutoPedidoEntity = modelMapper.map(produtoPedido, JpaProdutoPedidoEntity.class);
            jpaProdutoPedidoEntity.setPedido(jpaPedidoEntitySalvo);
            jpaProdutoPedidoRepository.save(jpaProdutoPedidoEntity);
        }

        return modelMapper.map(jpaPedidoEntitySalvo, Pedido.class);
    }

    @Override
    public Pedido atualizarStatusPedido(Pedido pedido) {
        JpaPedidoEntity jpaPedidoEntity = modelMapper.map(pedido, JpaPedidoEntity.class);
        return modelMapper.map(jpaPedidoRepository.save(jpaPedidoEntity), Pedido.class);
    }
}
