package com.jasonrharris.shop;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ReductionByPercentageWheTotalExceedsAmountRuleTest {

    private final ReductionByPercentageWheTotalExceedsAmountRule rule = new ReductionByPercentageWheTotalExceedsAmountRule(10,new BigDecimal("60"), 1L);

    @Test
    public void shouldApplyDiscountOnceTotalThresholdExceeded() {

        BillContext billContext = new BillContext();

        Product prod1 = Product.createProduct(1L, "Test1", "20");
        Product prod2 = Product.createProduct(2L, "Test2", "20");
        Product prod3 = Product.createProduct(3L, "Test1", "30");

        billContext.addProductItem(prod1);
        rule.addProductItem(billContext, prod1);
        billContext.addProductItem(prod2);
        rule.addProductItem(billContext, prod2);
        billContext.addProductItem(prod3);
        rule.addProductItem(billContext, prod3);

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("63.00")));
    }

    @Test
    public void shouldNotApplyDiscountIfThresholdIsNotExceeded() {

        BillContext billContext = new BillContext();

        Product prod1 = Product.createProduct(1L, "Test1", "20");
        Product prod2 = Product.createProduct(2L, "Test2", "20");

        billContext.addProductItem(prod1);
        rule.addProductItem(billContext, prod1);
        billContext.addProductItem(prod2);
        rule.addProductItem(billContext, prod2);

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("40.00")));

    }

    @Test
    public void shouldApplyDiscountOnceTotalThresholdIsMet() {

        BillContext billContext = new BillContext();

        Product prod1 = Product.createProduct(1L, "Test1", "30");
        Product prod3 = Product.createProduct(3L, "Test1", "30");

        billContext.addProductItem(prod1);
        rule.addProductItem(billContext, prod1);
        billContext.addProductItem(prod3);
        rule.addProductItem(billContext, prod3);

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("54.00")));
    }

    @Test
    public void shouldApplyDiscountWhePriceIsInTensOfPennies() {

        BillContext billContext = new BillContext();

        Product prod1 = Product.createProduct(1L, "Test1", "74.20");

        billContext.addProductItem(prod1);
        rule.addProductItem(billContext, prod1);

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("66.78")));

    }

    @Test
    public void shouldApplyDiscountWhenPriceIsInUnitsOfPenniesBiggerThan5() {
        BillContext billContext1 = new BillContext();

        Product prod2 = Product.createProduct(2L, "Test1", "109.78");

        billContext1.addProductItem(prod2);
        rule.addProductItem(billContext1, prod2);

        assertThat(billContext1.buildTotal(), equalTo(new BigDecimal("98.80")));
    }

    @Test
    public void shouldApplyDiscountWhenPriceIsInUnitsOfPenniesLessThan5() {
        BillContext billContext1 = new BillContext();

        Product prod2 = Product.createProduct(2L, "Test1", "109.73");

        billContext1.addProductItem(prod2);
        rule.addProductItem(billContext1, prod2);

        assertThat(billContext1.buildTotal(), equalTo(new BigDecimal("98.76")));
    }

    @Test
    public void shouldApplyDiscountWhenPriceIsLessThan100AndUnitsOfPenniesIs5() {
        BillContext billContext1 = new BillContext();

        Product prod2 = Product.createProduct(2L, "Test1", "81.95");

        billContext1.addProductItem(prod2);
        rule.addProductItem(billContext1, prod2);

        assertThat(billContext1.buildTotal(), equalTo(new BigDecimal("73.76")));
    }

    @Test
    public void shouldApplyDiscountWhenPriceIsLessThan100AndUnitsOfPenniesIsLessThan5() {
        BillContext billContext1 = new BillContext();

        Product prod2 = Product.createProduct(2L, "Test1", "81.94");

        billContext1.addProductItem(prod2);
        rule.addProductItem(billContext1, prod2);

        assertThat(billContext1.buildTotal(), equalTo(new BigDecimal("73.75")));
    }
}
