package br.com.fiap.techchallenge01.produto.adapter.in.controller;

import br.com.fiap.techchallenge01.produto.adapter.in.controller.api.CategoriaProdutoApi;
import br.com.fiap.techchallenge01.produto.application.service.CategoriaProdutoService;
import br.com.fiap.techchallenge01.produto.domain.CategoriaProduto;
import br.com.fiap.techchallenge01.produto.domain.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallenge01.produto.domain.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallenge01.produto.utils.mapper.CategoriaProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias-produto")
public class CategoriaProdutoController implements CategoriaProdutoApi {

    @Autowired
    private CategoriaProdutoMapper categoriaProdutoMapper;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaProdutoResponseDTO buscarCategoriaProduto(@PathVariable String id) {
        return categoriaProdutoMapper.toResponse(categoriaProdutoService.buscarCategoriaProdutoPorId(id));
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaProdutoResponseDTO> listarCategoriasProduto() {
        return categoriaProdutoMapper.toCollectionResponse(categoriaProdutoService.buscarCategoriasProduto());
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaProdutoResponseDTO cadastrarCategoriaProduto(@RequestBody CategoriaProdutoRequestDTO categoriaProdutoRequestDto) {

        CategoriaProduto categoriaProduto = categoriaProdutoMapper.toDomain(categoriaProdutoRequestDto);

        return categoriaProdutoMapper.toResponse(categoriaProdutoService.salvarCategoriaProduto(categoriaProduto));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaProdutoResponseDTO alterarNomeCategoriaProduto(@PathVariable String id, @RequestBody CategoriaProdutoRequestDTO categoriaProdutoRequestDto) {

        CategoriaProduto categoriaProduto = categoriaProdutoService.buscarCategoriaProdutoPorId(id);
        categoriaProduto.setNome(categoriaProdutoRequestDto.getNome());

        return categoriaProdutoMapper.toResponse(categoriaProdutoService.alterarNomeCategoriaProduto(id, categoriaProduto));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desativarCategoriaProduto(@PathVariable String id) {
        CategoriaProduto categoriaProduto = categoriaProdutoService.buscarCategoriaProdutoPorId(id);
        categoriaProdutoService.desativarCategoriaProduto(categoriaProduto);
    }
}
