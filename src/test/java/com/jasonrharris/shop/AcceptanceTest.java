package com.jasonrharris.shop;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AcceptanceTest {
    /**
     * Test data ---------
     * Basket: 001,002,003 Total price expected: £66.78
     * Basket: 001,003,001 Total price expected: £36.95
     * Basket: 001,002,001,003 Total price expected: £73.76
     */

    private static LocalYamlFileProductLoader productLoader = LocalYamlFileProductLoader.createLocalYamlFileProductLoader("products.yml");
    private static final Map<Long, Product> ID_TO_PRODUCT_MAP = productLoader.loadProducts();

    @Test
    public void shouldCalculateTotalPriceOfBasket001_002_003_Correctly() {

        Basket basket = addItemsToBasket(1L, 2L, 3L);

        calculateAmountAndCheck(basket, 66.78);

    }

    @Test
    public void shouldCalculateTotalPriceOfBasket001_003_001_Correctly() {

        Basket basket = addItemsToBasket(1L, 3L, 1L);

        calculateAmountAndCheck(basket, 36.95);

    }

    @Test
    public void shouldCalculateTotalPriceOfBasket001_002_001_003_Correctly() {

        Basket basket = addItemsToBasket(1L, 2L, 1L, 3L);

        calculateAmountAndCheck(basket, 73.76);

    }

    private Basket addItemsToBasket(Long... ids) {
        Basket basket = new ArrayListBasket();

        for (Long id : ids) {
            basket.addItem(ID_TO_PRODUCT_MAP.get(id));
        }

        return basket;
    }

    private void calculateAmountAndCheck(Basket basket, double expectedBalance) {
        Checkout checkout = Main.buildCheckout();

        basket.getBasketItems().forEach(checkout::scan);

        assertThat(checkout.total(), equalTo(expectedBalance));
    }
}
