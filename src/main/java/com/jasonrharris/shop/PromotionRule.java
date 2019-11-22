package com.jasonrharris.shop;

interface PromotionRule {
    void addProductItem(BillContext billContext, Product itemProduct);
    Long getPromotionalRuleId();

}
