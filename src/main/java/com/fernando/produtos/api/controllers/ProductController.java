package com.fernando.produtos.api.controllers;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.dtos.requests.CreateProductRequest;
import com.fernando.produtos.api.dtos.views.ProductListView;
import com.fernando.produtos.api.models.Product;
import com.fernando.produtos.api.services.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Listar produtos", description = "Retorna uma lista de produtos filtrada")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<List<ProductListView>> getByFilter(
            @Parameter(description = "Filtro para listagem de produtos", required = false)
            @Valid
            ProductListFilter filter
            ){

        var products = productService.getByFilters(filter);

        var views = products.stream().map(ProductListView::getView).toList();

        return ResponseEntity.ok(views);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @Operation(summary = "Obtem detalhes do produto", description = "Retorna uma visualizacao do produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(implementation = Exception.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    public ResponseEntity<Product> getDetails(
            @Parameter(description = "ID único do produto", required = true, example = "123")
            @PathVariable("id") Long id
    ) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponse(responseCode = "200", description = "Produto criado com sucesso")
    public ResponseEntity<ProductListView> createProduct(
            @Parameter(description = "Dados do produto a ser criado", required = true)
            @RequestBody CreateProductRequest request)
    {
        var response = productService.create(request.getName(), request.getDescription(), request.getPrice());

        return ResponseEntity.ok(ProductListView.getView(response));
    }

}
