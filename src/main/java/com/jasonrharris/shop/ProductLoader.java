package com.jasonrharris.shop;

import java.util.Map;

public interface ProductLoader {
    Map<Long,Product> loadProducts();
}
