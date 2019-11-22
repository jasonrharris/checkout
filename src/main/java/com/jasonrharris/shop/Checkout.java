package com.jasonrharris.shop;

public interface Checkout {
    void scan(Product product);
    Double total();

}
