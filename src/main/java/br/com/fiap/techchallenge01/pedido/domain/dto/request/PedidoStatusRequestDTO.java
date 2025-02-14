package br.com.fiap.techchallenge01.pedido.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoStatusRequestDTO {

    @NotBlank
    private String status;
}