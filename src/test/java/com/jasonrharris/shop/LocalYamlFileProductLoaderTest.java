package com.jasonrharris.shop;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.Map;

import static org.junit.Assert.*;

public class LocalYamlFileProductLoaderTest {

    @Test
    public void loadProducts() {
        LocalYamlFileProductLoader loader = LocalYamlFileProductLoader.createLocalYamlFileProductLoader("products.yml");

        Map<Long, Product> idToProductMap = loader.loadProducts();

        assertFalse(idToProductMap.isEmpty());

        assertEquals(idToProductMap.get(1L).getName(),"Travel Card Holder");
    }
}
