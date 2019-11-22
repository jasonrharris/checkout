package com.jasonrharris.shop;

public class ProductDTO {
    private long code;
    private String name;
    private String price;

    public ProductDTO() {}

    public void setCode(long code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    long getCode() {
        return code;
    }

    String getName() {
        return name;
    }

    String getPrice() {
        return price;
    }
}
