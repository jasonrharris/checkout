package com.jasonrharris.shop;

import java.util.List;

public class BasicPromotionApplyingCheckoutFactory implements CheckoutFactory {
    @Override
    public Checkout createBasicPromotionApplyingCheckout(List<PromotionRule> promotionRules) {
        return new BasicPromotionApplyingCheckout(promotionRules);
    }
}
