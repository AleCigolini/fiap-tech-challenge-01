package br.com.fiap.techchallenge01.pedido.application.service;

import br.com.fiap.techchallenge01.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.core.utils.domain.Cpf;
import br.com.fiap.techchallenge01.pedido.application.usecase.PedidoUseCase;
import br.com.fiap.techchallenge01.pedido.domain.Pagamento;
import br.com.fiap.techchallenge01.pedido.domain.Pedido;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.response.PedidoResponseDTO;
import br.com.fiap.techchallenge01.pedido.domain.repository.PagamentoRepository;
import br.com.fiap.techchallenge01.pedido.domain.repository.PedidoRepository;
import br.com.fiap.techchallenge01.pedido.utils.mapper.PedidoMapper;
import br.com.fiap.techchallenge01.pedido.utils.mapper.StatusPedido;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService implements PedidoUseCase {
    private PedidoRepository pedidoRepository;
    private PagamentoRepository pagamentoRepository;
    private PedidoMapper pedidoMapper;
    private ConsultarClienteUseCase consultarClienteUseCase;
    private ModelMapper modelMapper;


    @Override
    public List<PedidoResponseDTO> buscarPedidos() {
        List<Pedido> pedidos = pedidoRepository.buscarPedidos();
        return pedidoMapper.toCollectionResponse(pedidos);
    }

    @Override
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {

        Cliente cliente = obterClientePorCpfOuEmail(pedidoRequestDTO);

        Pedido pedido = pedidoMapper.toPedido(pedidoRequestDTO);
        pedido.setCliente(cliente);

        Pedido pedidoSalvo = pedidoRepository.criarPedido(pedido);
        enviarPagamento(pedidoSalvo);

        return pedidoMapper.toResponse(pedidoSalvo);
    }

    private Cliente obterClientePorCpfOuEmail(PedidoRequestDTO pedidoRequestDTO) {
        if (!Strings.isEmpty(pedidoRequestDTO.getCliente().getCpf())) {
            return consultarClienteUseCase.buscarClientePorCpf(modelMapper.map(pedidoRequestDTO.getCliente().getCpf(), Cpf.class));
        } else {
            return consultarClienteUseCase.buscarClientePorEmail(pedidoRequestDTO.getCliente().getEmail());
        }
    }

    private void enviarPagamento(Pedido pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPreco(pedido.getPreco());
        pagamento.setCodigoPedido(pedido.getId());
        Pagamento pagamentoEfetuado = pagamentoRepository.enviarPagamento(pagamento);

        pedido.setStatus(StatusPedido.APROVADO.toString());
        pedido.setCodigoPagamento(pagamentoEfetuado.getId());
        pedido.setDataAtualizacao(OffsetDateTime.now());
        pedidoRepository.atualizarStatusPedido(pedido);
    }
}