package br.com.fiap.techchallenge01.pagamento.adapter.out.repository;

import br.com.fiap.techchallenge01.pagamento.adapter.out.entity.JpaPagamentoEntity;
import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge01.pagamento.domain.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    @Autowired
    private JpaPagamentoRepository jpaPagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Pagamento enviarPagamento(Pagamento pagamento) {
        JpaPagamentoEntity jpaPagamentoEntity = modelMapper.map(pagamento, JpaPagamentoEntity.class);

        JpaPagamentoEntity jpaPagamentoEntitySalvo = jpaPagamentoRepository.save(jpaPagamentoEntity);

        return modelMapper.map(jpaPagamentoEntitySalvo, Pagamento.class);
    }

    @Override
    public Optional<Pagamento> buscarPagamentoPorPedidoId(String idPedido) {
        return jpaPagamentoRepository.findByCodigoPedido(idPedido).map(jpaPagamentoEntity ->  modelMapper.map(jpaPagamentoEntity, Pagamento.class));
    }
}
