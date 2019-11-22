package com.jasonrharris.shop;

import java.util.List;

public interface Basket {
    List<Product> getBasketItems();

    Basket addItem(Product product);
}
