package com.fernando.produtos.api.dtos.views;


import com.fernando.produtos.api.models.Product;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductListView implements Serializable {

    private String name;

    private BigDecimal price;

    private ProductListView() {
    }

    public static ProductListView getView(Product product){
        var view = new ProductListView();
        view.setName(product.getName());
        view.setPrice(product.getPrice());
        return view;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
