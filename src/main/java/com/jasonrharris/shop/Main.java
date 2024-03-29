package com.jasonrharris.shop;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    private static final String DEFAULT_PRODUCT_FILE_PATH = "products.yml";

    public static void main(String[] args) {

        Map<Long, Product> productMap = loadAvailableProducts();

        Basket basket = addProductToBasketFromCommandLine(productMap);

        Checkout basicPromotionApplyingCheckout = buildCheckout();

        basket.getBasketItems().forEach(basicPromotionApplyingCheckout::scan);

        System.out.println("Total at checkout is: " + basicPromotionApplyingCheckout.total());

    }

    private static Map<Long, Product> loadAvailableProducts() {
        ProductLoader productLoader = LocalYamlFileProductLoader.createLocalYamlFileProductLoader(DEFAULT_PRODUCT_FILE_PATH);

        return productLoader.loadProducts();
    }

    private static Basket addProductToBasketFromCommandLine(Map<Long, Product> productMap) {
        Basket basket = new ArrayListBasket();

        boolean exit = false;

        try (Scanner scanner = new Scanner(System.in)) {

            try {
                while (!exit) {
                    System.out.println("Enter a product code to add to the basket: ");

                    String prodCode = scanner.next();

                    if (prodCode.equalsIgnoreCase("Exit")) {
                        exit = true;
                    } else {
                        Long code = Long.valueOf(prodCode);
                        Product product = productMap.get(code);
                        System.out.println("Product is " + product);
                        if (product == null) {
                            System.out.println(code + " is not a known product code");
                        } else {
                            basket.addItem(product);
                        }
                    }
                }

            } catch (IllegalStateException | NoSuchElementException e) {
                // System.in has been closed
                System.out.println("System.in was closed");
                e.printStackTrace(System.err);
            }
        }

        return basket;
    }

    /*
    These are just substitutes for a persistent version of promotional rule storage
     */
    private static final long TRAVEL_CARD_HOLDER_ID = 1L;
    private static final int NO_OF_HOLDERS_TO_TRIGGER_DISCOUNT = 2;
    private static final BigDecimal MULTI_HOLDER_PURCHASE_PRICE = new BigDecimal("8.50");
    private static final BigDecimal TOTAL_BILL_AMOUNT_TO_RECEIVE_DISCOUNT = new BigDecimal("60");
    private static final int TOTAL_BILL_DISCOUNT = 10;
    private static final long TOTAL_AMOUNT_RULE_ID = 2L;
    private static final long MULTI_HOLDER_PURCHASE_RULE = 1L;

    static Checkout buildCheckout() {
        CheckoutFactory checkoutFactory = new BasicPromotionApplyingCheckoutFactory();

        ReductionByPercentageWheTotalExceedsAmountRule totalAmountRule = new ReductionByPercentageWheTotalExceedsAmountRule(TOTAL_BILL_DISCOUNT, TOTAL_BILL_AMOUNT_TO_RECEIVE_DISCOUNT, TOTAL_AMOUNT_RULE_ID);

        XOrMoreProductsPurchasedForDiscountedPricePromotionRule multiProductRule = new XOrMoreProductsPurchasedForDiscountedPricePromotionRule(TRAVEL_CARD_HOLDER_ID, NO_OF_HOLDERS_TO_TRIGGER_DISCOUNT, MULTI_HOLDER_PURCHASE_PRICE, MULTI_HOLDER_PURCHASE_RULE);

        List<PromotionRule> promotionRules = Arrays.asList(totalAmountRule, multiProductRule);

        return checkoutFactory.createBasicPromotionApplyingCheckout(promotionRules);
    }

}
