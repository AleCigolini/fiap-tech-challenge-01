package br.com.fiap.techchallenge01.pedido.domain;

import br.com.fiap.techchallenge01.core.utils.domain.DominioBase;
import br.com.fiap.techchallenge01.produto.domain.Produto;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ProdutoPedido extends DominioBase {

    private String id;
    private Long quantidade;
    private String observacao;
    private Produto produto;
}