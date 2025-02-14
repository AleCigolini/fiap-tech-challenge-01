package br.com.fiap.techchallenge01.pedido.adapter.in.controller.api;

import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.request.PedidoStatusRequestDTO;
import br.com.fiap.techchallenge01.pedido.domain.dto.response.PedidoResponseDTO;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "${tag.swagger.pedido.name}", description = "${tag.swagger.pedido.description}")
public interface PedidoApi {

    /**
     * Busca todos os pedidos
     *
     * @return {@link PedidoResponseDTO}
     */
    @Operation(summary = "Buscar todos os pedidos",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Cliente não encntrado",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<List<PedidoResponseDTO>> buscarPedidos();

    /**
     * Criar pedido
     *
     * @param pedidoRequestDTO DTO para criação de pedido
     * @return {@link PedidoResponseDTO}
     */
    @Operation(summary = "Criar novo pedido",
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "Erro no cadastro do pedido/Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
            })
    ResponseEntity<PedidoResponseDTO> criarPedido(PedidoRequestDTO pedidoRequestDTO) throws URISyntaxException;

    /**
     * Atualizar status do pedido
     *
     * @param pedidoStatusRequestDTO DTO para atualização do status do pedido
     * @param id ID do pedido a ter seu status atualizado
     * @return {@link PedidoResponseDTO}
     */
    @Operation(summary = "Atualizar o status de um pedido")
    ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(PedidoStatusRequestDTO pedidoStatusRequestDTO, String id);

    /**
     * Verifica status do pagamento do pedido por ID do pedido
     *
     * @param idPedido ID do pedido
     * @return {@link PagamentoResponseDTO}
     */
    @Operation(summary = "Status do pagamento do pedido por ID do pedido")
    ResponseEntity<PagamentoResponseDTO> verificarPagamentoPedido(String idPedido);
}