package com.jasonrharris.shop;

import java.util.List;

public interface CheckoutFactory {
    Checkout createBasicPromotionApplyingCheckout(List<PromotionRule> promotionRules);
}
