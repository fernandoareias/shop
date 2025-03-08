package com.fernando.produtos.api.controllers;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.dtos.requests.CreateProductRequest;
import com.fernando.produtos.api.dtos.views.ProductListView;
import com.fernando.produtos.api.services.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Listar produtos", description = "Retorna uma lista de produtos filtrada")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    public ResponseEntity<ProductListView> getByFilter(
            @Parameter(description = "Filtro para listagem de produtos", required = false)
            @Valid
            ProductListFilter filter
            ){

        var response = productService.getByFilters(filter);

        return ResponseEntity.ok(null);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    @ApiResponse(responseCode = "200", description = "Produto criado com sucesso")
    public ResponseEntity<ProductListView> createProduct(
            @Parameter(description = "Dados do produto a ser criado", required = true)
            @RequestBody CreateProductRequest request)
    {
        var response = productService.Create(request.getName(), request.getDescription(), request.getPrice());

        return ResponseEntity.ok(ProductListView.getView(response));
    }

}
