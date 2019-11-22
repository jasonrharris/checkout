package com.jasonrharris.shop;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BasicPromotionApplyingCheckoutTest {

    @Test
    public void shouldOutputCorrectTotalWithNoPromotionalRules() {
        BasicPromotionApplyingCheckout checkout = new BasicPromotionApplyingCheckout(Collections.emptyList());

        Product product1 = Product.createProduct(1L, "Test 1", "20.00");

        checkout.scan(product1);
        checkout.scan(product1);
        checkout.scan(Product.createProduct(2L, "Test 2", "25.00"));

        assertThat(checkout.total(), equalTo(65.00));
    }

    @Test
    public void shouldOutputCorrectTotalWithOnePromotionalRule() {
        long discountedProductCode = 1L;
        XOrMoreProductsPurchasedForDiscountedPricePromotionRule o = new XOrMoreProductsPurchasedForDiscountedPricePromotionRule(discountedProductCode, 1, new BigDecimal("15"), 1L);

        BasicPromotionApplyingCheckout checkout = new BasicPromotionApplyingCheckout(Collections.singletonList(o));

        Product product1 = Product.createProduct(discountedProductCode, "Test 1", "20.00");

        checkout.scan(product1);
        checkout.scan(product1);
        checkout.scan(Product.createProduct(2L, "Test 2", "25.00"));

        assertThat(checkout.total(), equalTo(55.00));
    }

    @Test
    public void shouldOutputCorrectTotalWithOneItemSpecificPromotionalRuleAndOneTotalBillRule() {
        long discountedProductCode = 1L;

        ReductionByPercentageWheTotalExceedsAmountRule totalAmountRule = new ReductionByPercentageWheTotalExceedsAmountRule(10, new BigDecimal("60"), 2L);

        XOrMoreProductsPurchasedForDiscountedPricePromotionRule multiProductRule = new XOrMoreProductsPurchasedForDiscountedPricePromotionRule(discountedProductCode, 1, new BigDecimal("15"), 1L);

        BasicPromotionApplyingCheckout checkout = new BasicPromotionApplyingCheckout(Arrays.asList(totalAmountRule, multiProductRule));

        Product product1 = Product.createProduct(discountedProductCode, "Test 1", "20.00");

        checkout.scan(product1);
        checkout.scan(product1);
        Product product2 = Product.createProduct(2L, "Test 2", "25.00");
        checkout.scan(product2);
        checkout.scan(product2);

        assertThat(checkout.total(), equalTo(72.0));
    }
}
