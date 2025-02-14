package br.com.fiap.techchallenge01.pedido.application.service;

import br.com.fiap.techchallenge01.cliente.application.service.ClienteService;
import br.com.fiap.techchallenge01.cliente.domain.Cliente;
import br.com.fiap.techchallenge01.pagamento.application.usecase.PagamentoUseCase;
import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge01.pedido.application.exception.PedidoNaoEncontradoException;
import br.com.fiap.techchallenge01.pedido.application.usecase.PedidoUseCase;
import br.com.fiap.techchallenge01.pedido.domain.Pedido;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoStatusRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.response.PedidoResponseDTO;
import br.com.fiap.techchallenge01.pedido.domain.repository.PedidoRepository;
import br.com.fiap.techchallenge01.pedido.utils.mapper.PedidoMapper;
import br.com.fiap.techchallenge01.pedido.utils.mapper.StatusPedido;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PedidoService implements PedidoUseCase {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoUseCase pagamentoUseCase;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ClienteService clienteService;

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

    @Override
    @Transactional(readOnly = true)
    public PagamentoResponseDTO verificarPagamentoPedido(String idPedido) {
        return pagamentoUseCase.verificarPagamentoPedido(idPedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO atualizarStatusPedido(PedidoStatusRequestDTO pedidoStatusRequestDTO, String id) {
        Pedido pedido = pedidoRepository.buscarPedidoPorId(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
        pedido.setStatus(pedidoStatusRequestDTO.getStatus());
        Pedido pedidoSalvo = pedidoRepository.atualizarStatusPedido(pedido);
        return pedidoMapper.toResponse(pedidoSalvo);
    }

    private Cliente obterClientePorCpfOuEmail(PedidoRequestDTO pedidoRequestDTO) {
        if (!Strings.isEmpty(pedidoRequestDTO.getCliente().getCpf())) {
            return clienteService.buscarClientePorCpf(pedidoRequestDTO.getCliente().getCpf());
        } else {
            return clienteService.buscarClientePorEmail(pedidoRequestDTO.getCliente().getEmail());
        }
    }

    private void enviarPagamento(Pedido pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setPreco(pedido.getPreco());
        pagamento.setCodigoPedido(pedido.getId());
        Pagamento pagamentoEfetuado = pagamentoUseCase.enviarPagamento(pagamento);

        pedido.setStatus(StatusPedido.RECEBIDO.toString());
        pedido.setCodigoPagamento(pagamentoEfetuado.getId());
        pedido.setDataAtualizacao(OffsetDateTime.now());
        pedidoRepository.atualizarStatusPedido(pedido);
    }
}