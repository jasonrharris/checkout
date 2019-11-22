package com.jasonrharris.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {
    private final long code;
    private final String name;
    private final BigDecimal price;

    private Product(long code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public static Product createProduct(long code, String name, String price) {
        return new Product(code, name, new BigDecimal(price).setScale(2, RoundingMode.HALF_UP));
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code == product.code &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
