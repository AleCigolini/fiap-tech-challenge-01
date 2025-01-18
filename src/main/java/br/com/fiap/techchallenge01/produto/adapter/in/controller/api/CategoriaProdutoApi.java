package br.com.fiap.techchallenge01.produto.adapter.in.controller.api;

import br.com.fiap.techchallenge01.produto.domain.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallenge01.produto.domain.dto.response.CategoriaProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "${tag.swagger.categoriaproduto.name}", description = "${tag.swagger.categoriaproduto.description}")
public interface CategoriaProdutoApi {

    /**
     * Busca a categoria de produto a partir de seu ID.
     *
     * @param id String da categoria de produto
     * @return {@link CategoriaProdutoResponseDTO}
     */
    @Operation(summary = "Buscar categoria de produto com Id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da categoria de produto inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Categoria de produto não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    CategoriaProdutoResponseDTO buscarCategoriaProduto(@Parameter(description = "ID da categoria de produto", example = "1", required = true) String id);

    /**
     * Listar todas as categorias de produto.
     *
     * @return Lista de {@link CategoriaProdutoResponseDTO}
     */
    @Operation(summary = "Listar todas as categorias de produto",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    List<CategoriaProdutoResponseDTO> listarCategoriasProduto();

    /**
     * Cadastrar uma nova categoria de produto.
     *
     * @param categoriaProdutoRequestDto Dados da categoria de produto
     * @return {@link CategoriaProdutoRequestDTO}
     */
    @Operation(summary = "Cadastro de categoria de produto",
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "Dados da categoria de produto inválidos",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    CategoriaProdutoResponseDTO cadastrarCategoriaProduto(CategoriaProdutoRequestDTO categoriaProdutoRequestDto);

    /**
     * Alterar o nome de uma categoria de produto.
     *
     * @param id                         String da categoria de produto
     * @param categoriaProdutoRequestDto Dados da categoria de produto
     * @return {@link CategoriaProdutoResponseDTO}
     */
    @Operation(summary = "Alterar o nome de uma categoria de produto",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Categoria de produto não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    CategoriaProdutoResponseDTO alterarNomeCategoriaProduto(
            @Parameter(description = "ID da categoria de produto", example = "1", required = true) String id,
            CategoriaProdutoRequestDTO categoriaProdutoRequestDto);


    /**
     * Desativar uma categoria de produto.
     *
     * @param id String da categoria de produto
     * @return {@link CategoriaProdutoResponseDTO}
     */
    @Operation(summary = "Desativar uma categoria de produto",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "ID da categoria de produto inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Categoria de produto não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    void desativarCategoriaProduto(
            @Parameter(description = "ID da categoria de produto", example = "1", required = true) String id);

}