package com.jasonrharris.shop;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class XOrMoreProductsPurchasedForDiscountedPricePromotionRuleTest {

    @Test
    public void shouldOnlyAdjustBillContextIfNumberOfBillItemsEqualsOrExceedsThreshold() {
        long productCode = 1L;

        XOrMoreProductsPurchasedForDiscountedPricePromotionRule rule = new XOrMoreProductsPurchasedForDiscountedPricePromotionRule(productCode,3,new BigDecimal("8.50"), 1L);

        Product product = Product.createProduct(productCode, "Discounted Proud", "12.50");

        BillContext billContext = new BillContext();

        billContext.addProductItem(product);

        rule.addProductItem(billContext, product);

        assertThat(billContext.buildTotal().toString(), equalTo("12.50"));

        billContext.addProductItem(product);

        rule.addProductItem(billContext, product);

        assertThat(billContext.buildTotal().toString(), equalTo("25.00"));

        billContext.addProductItem(product);

        rule.addProductItem(billContext, product);

        assertThat(billContext.buildTotal().toString(), equalTo("25.50"));


    }
}
