package com.fernando.produtos.api.dtos.filters;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Filtros opcionais para listagem de produtos")
public class ProductListFilter {

    @Schema(description = "Filtrar por nome do produto", example = "Smartphone")
    private String name;

    @Schema(description = "Preço mínimo", example = "100.00")
    private BigDecimal priceGreaterThan;

    @Schema(description = "Preço máximo", example = "500.00")
    private BigDecimal priceLessThan;

    @NotNull(message = "O número da página é obrigatório")
    @Min(value = 0, message = "O número da página deve ser >= 0")
    @Schema(description = "Número da página", example = "0", required = true)
    private Integer page;

    @NotNull(message = "O tamanho da página é obrigatório")
    @Min(value = 1, message = "O tamanho da página deve ser >= 1")
    @Schema(description = "Quantidade de itens por página", example = "10", required = true)
    private Integer size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public void setPriceGreaterThan(BigDecimal priceGreaterThan) {
        this.priceGreaterThan = priceGreaterThan;
    }

    public BigDecimal getPriceLessThan() {
        return priceLessThan;
    }

    public void setPriceLessThan(BigDecimal priceLessThan) {
        this.priceLessThan = priceLessThan;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

