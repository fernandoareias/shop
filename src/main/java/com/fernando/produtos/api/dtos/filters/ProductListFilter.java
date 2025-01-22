package com.fernando.produtos.api.dtos.filters;

import java.math.BigDecimal;

public class ProductListFilter {

    private String name;
    private BigDecimal priceGreaterThan;
    private BigDecimal priceLessThan;
    private Integer page;
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

