package com.jasonrharris.shop;

import java.math.BigDecimal;

public class XOrMoreProductsPurchasedForDiscountedPricePromotionRule implements PromotionRule {
    private final long productCode;
    private final int discountThreshold;
    private final BigDecimal discountPrice;
    private final long id;

    public XOrMoreProductsPurchasedForDiscountedPricePromotionRule(long productCode, int discountThreshold, BigDecimal discountPrice, long id) {
        this.productCode = productCode;
        this.discountThreshold = discountThreshold;
        this.discountPrice = discountPrice;
        this.id = id;
    }

    @Override
    public void addProductItem(BillContext billContext, Product itemProduct) {
        if (itemProduct.getCode() == productCode){
            if (billContext.getProductCount(itemProduct) >= discountThreshold) {
                billContext.modifyPrices(itemProduct, discountPrice);
            }
        };
    }

    @Override
    public Long getPromotionalRuleId() {
        return id;
    }
}
