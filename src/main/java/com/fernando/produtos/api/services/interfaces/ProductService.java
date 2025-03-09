package com.fernando.produtos.api.services.interfaces;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.models.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<Product> getByFilters(ProductListFilter filter);
    Optional<Product> getById(Long id);
    Product create(String name, String description, BigDecimal price);

}
