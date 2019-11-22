package com.jasonrharris.shop;

import java.util.List;

public class BasicPromotionApplyingCheckout implements Checkout {
    private final List<PromotionRule> promotionRules;

    private final BillContext billContext = new BillContext();

    BasicPromotionApplyingCheckout(List<PromotionRule> promotionRules) {
        this.promotionRules = promotionRules;
    }

    @Override
    public void scan(Product product) {
        billContext.addProductItem(product);
        for(PromotionRule promotionRule: promotionRules) {
            promotionRule.addProductItem(this.billContext, product);
        }
    }

    @Override
    public Double total() {
        return billContext.buildTotal().doubleValue();
    }
}
