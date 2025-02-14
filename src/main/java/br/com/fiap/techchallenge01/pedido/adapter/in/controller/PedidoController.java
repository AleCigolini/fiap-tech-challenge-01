package br.com.fiap.techchallenge01.pedido.adapter.in.controller;

import br.com.fiap.techchallenge01.pedido.adapter.in.controller.api.PedidoApi;
import br.com.fiap.techchallenge01.pedido.application.usecase.PedidoUseCase;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoStatusRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.response.PedidoResponseDTO;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoApi {

    private final PedidoUseCase pedidoUseCase;

    @Override
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidos() {
        List<PedidoResponseDTO> pedidosResponseDTO = pedidoUseCase.buscarPedidos();

        return ResponseEntity.ok(pedidosResponseDTO);
    }

    @Override
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) throws URISyntaxException {
        PedidoResponseDTO pedidoResponse = pedidoUseCase.criarPedido(pedidoRequestDTO);

        return ResponseEntity.created(new URI("/pedidos/" + pedidoResponse.getId())).body(pedidoResponse);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(@RequestBody @Valid PedidoStatusRequestDTO pedidoStatusRequestDTO, @PathVariable String id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoUseCase.atualizarStatusPedido(pedidoStatusRequestDTO, id);

        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @Override
    @GetMapping("/{idPedido}/pagamento")
    public ResponseEntity<PagamentoResponseDTO> verificarPagamentoPedido(@PathVariable String idPedido) {
        PagamentoResponseDTO pagamentoResponseDTO = pedidoUseCase.verificarPagamentoPedido(idPedido);

        return ResponseEntity.ok(pagamentoResponseDTO);
    }
}