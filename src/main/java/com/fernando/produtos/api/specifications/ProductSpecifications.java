package com.fernando.produtos.api.specifications;

import com.fernando.produtos.api.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {

    public static Specification<Product> hasName(String name) {

        if(name == null || name.trim().isEmpty())
            return null;

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasMinPrice(BigDecimal minPrice) {

        if (minPrice == null || minPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(BigDecimal maxPrice) {
        if (maxPrice == null || maxPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
