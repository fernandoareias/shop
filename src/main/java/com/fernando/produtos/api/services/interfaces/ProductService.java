package com.fernando.produtos.api.services.interfaces;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.models.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {

    Page<Product> getByFilters(ProductListFilter filter);
    Product Create(String name, String description, BigDecimal price);
}
