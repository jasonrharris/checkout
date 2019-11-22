package com.jasonrharris.shop;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BillContextTest {

    private final PromotionRule promotionalRule1 = new PromotionRule() {
        @Override
        public void addProductItem(BillContext billContext, Product itemProduct) {
        }

        @Override
        public Long getPromotionalRuleId() {
            return 1L;
        }


    };
    private final PromotionRule promotionalRule2 = new PromotionRule() {
        @Override
        public void addProductItem(BillContext billContext, Product itemProduct) {
        }

        @Override
        public Long getPromotionalRuleId() {
            return 2L;
        }


    };

    @Test
    public void shouldBeAbleToAddAProductItemAndGetCountAndTotalRight() {
        BillContext billContext = new BillContext();
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        billContext.addProductItem(testProduct);
        assertThat(billContext.getProductCount(testProduct), equalTo(1));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("22.30")));
    }

    @Test
    public void shouldBeAbleToAddManyProductItemsAndGetTheProductCountAndTotalPriceRight() {
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        Product testProduct2 = Product.createProduct(2, "Test2", "34.40");

        BillContext billContext = getPopulatedBillContext(testProduct, testProduct2);

        assertThat(billContext.getProductCount(testProduct), equalTo(2));
        assertThat(billContext.getProductCount(testProduct2), equalTo(2));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("113.40")));
    }

    @Test
    public void shouldBeAbleToModifyPricesParticularProductTypesPrices() {
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        Product testProduct2 = Product.createProduct(2, "Test2", "34.40");

        BillContext billContext = getPopulatedBillContext(testProduct, testProduct2);

        billContext.modifyPrices(testProduct, new BigDecimal("12.50"));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("93.80")));

    }

    @Test
    public void shouldBeAbleToModifyTotalAmountCalculationWithAFunction() {
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        Product testProduct2 = Product.createProduct(2, "Test2", "34.40");

        BillContext billContext = getPopulatedBillContext(testProduct, testProduct2);

        billContext.addNewTotalBillAdjustmentFunction(promotionalRule2, bigDecimal -> bigDecimal.subtract(new BigDecimal(10)));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("103.40")));

    }

    @Test
    public void shouldBeAbleToModifyTotalAmountCorrectlyWhenFunctionWithDifferentPromotionalRuleIdIsAddedTwice() {
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        Product testProduct2 = Product.createProduct(2, "Test2", "34.40");

        BillContext billContext = getPopulatedBillContext(testProduct, testProduct2);

        billContext.addNewTotalBillAdjustmentFunction(promotionalRule2, bigDecimal -> bigDecimal.subtract(new BigDecimal(10)));

        billContext.addNewTotalBillAdjustmentFunction(
                promotionalRule1, bigDecimal -> bigDecimal.subtract(new BigDecimal(10)));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("93.40")));

    }

    @Test
    public void shouldBeAbleToModifyTotalAmountCorrectlyWhenFunctionWithSamePromotionalRuleIdIsAddedTwice() {
        Product testProduct = Product.createProduct(1, "Test1", "22.30");
        Product testProduct2 = Product.createProduct(2, "Test2", "34.40");

        BillContext billContext = getPopulatedBillContext(testProduct, testProduct2);

        billContext.addNewTotalBillAdjustmentFunction(promotionalRule1, bigDecimal -> bigDecimal.subtract(new BigDecimal(10)));

        billContext.addNewTotalBillAdjustmentFunction(
                promotionalRule1, bigDecimal -> bigDecimal.subtract(new BigDecimal(10)));

        assertThat(billContext.buildTotal(), equalTo(new BigDecimal("103.40")));

    }

    private BillContext getPopulatedBillContext(Product testProduct, Product testProduct2) {
        BillContext billContext = new BillContext();
        billContext.addProductItem(testProduct).addProductItem(testProduct);
        billContext.addProductItem(testProduct2).addProductItem(testProduct2);
        return billContext;
    }
}
