package br.com.fiap.techchallenge01.pedido.utils.mapper;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedido {

    ABERTO("Aberto"),
    RECEBIDO("Recebido"),
    EM_ANDAMENTO("Em preparação"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String status;

    StatusPedido(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}