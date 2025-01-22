package com.fernando.produtos.api.services;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.models.Product;
import com.fernando.produtos.api.repositories.ProductsRepository;
import com.fernando.produtos.api.services.interfaces.ProductService;
import com.fernando.produtos.api.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProductServices implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Page<Product> getByFilters(ProductListFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").ascending());

        return productsRepository.findAll(
                Specification.where(filter.getName() != null ? ProductSpecifications.hasName(filter.getName()) : null)
                        .and(filter.getPriceLessThan() != null ? ProductSpecifications.hasMaxPrice(filter.getPriceLessThan()) : null)
                        .and(filter.getPriceGreaterThan() != null ? ProductSpecifications.hasMinPrice(filter.getPriceGreaterThan()) : null),
                pageable
        );
    }

    @Override
    @Transactional
    public Product Create(String name, String description, BigDecimal price) {
        var product = new Product(name, price, description);
        productsRepository.save(product);
        return product;
    }
}
