package br.com.fiap.techchallenge01.pagamento.mapper;

import br.com.fiap.techchallenge01.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge01.produto.application.service.ProdutoService;
import br.com.fiap.techchallenge01.pagamento.domain.dto.response.PagamentoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoService produtoService;

    public PagamentoResponseDTO toResponse(Pagamento pagamento) {
        return modelMapper.map(pagamento, PagamentoResponseDTO.class);
    }
}