package com.fernando.produtos.api.services;

import com.fernando.produtos.api.dtos.filters.ProductListFilter;
import com.fernando.produtos.api.models.Product;
import com.fernando.produtos.api.repositories.ProductsRepository;
import com.fernando.produtos.api.services.interfaces.ProductService;
import com.fernando.produtos.api.specifications.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServices implements ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Page<Product> getByFilters(ProductListFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), Sort.by("id").ascending());

        return productsRepository.findAll(
                Specification
                        .where(ProductSpecifications.hasName(filter.getName()))
                        .and(ProductSpecifications.hasMaxPrice(filter.getPriceLessThan()))
                        .and(ProductSpecifications.hasMinPrice(filter.getPriceGreaterThan())),
                pageable
        );
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public Optional<Product> getById(Long id) {
        return productsRepository.findById(id);
    }

    @Override
    @Transactional
    public Product create(String name, String description, BigDecimal price) {
        var product = Product.get(name, price, description);
        productsRepository.save(product);
        return product;
    }
}
