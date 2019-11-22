package com.jasonrharris.shop;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasket implements Basket {
    private final List<Product> basketItems = new ArrayList<>();

    @Override
    public List<Product> getBasketItems() {
        return basketItems;
    }

    @Override
    public Basket addItem(Product product) {
        basketItems.add(product);
        return this;
    }
}
