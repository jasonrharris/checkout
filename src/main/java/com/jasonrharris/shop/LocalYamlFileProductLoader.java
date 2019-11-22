package com.jasonrharris.shop;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LocalYamlFileProductLoader implements ProductLoader {
    private final String filePath;
    private final Yaml yamlWithProductConstructor;

    private LocalYamlFileProductLoader(String filePath, Yaml yamlWithProductConstructor) {
        this.filePath = filePath;
        this.yamlWithProductConstructor = yamlWithProductConstructor;
    }

    static LocalYamlFileProductLoader createLocalYamlFileProductLoader(String filePath) {
        Yaml yamlWithProductConstructor = new Yaml(new Constructor(ProductDTO.class));
        return new LocalYamlFileProductLoader(filePath, yamlWithProductConstructor);
    }

    @Override
    public Map<Long, Product> loadProducts() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);
        Iterable<Object> obj = yamlWithProductConstructor.loadAll(inputStream);

        Map<Long, Product> idToProdMap = new HashMap<>();

        obj.forEach(o -> {
            if (o instanceof ProductDTO) {
                ProductDTO prodDTO = (ProductDTO) o;
                Product prod = Product.createProduct(prodDTO.getCode(), prodDTO.getName(), prodDTO.getPrice());
                idToProdMap.put(prod.getCode(), prod);
            }
        });
        return idToProdMap;
    }
}
