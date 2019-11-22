package com.jasonrharris.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReductionByPercentageWheTotalExceedsAmountRule implements PromotionRule {
    private static final BigDecimal PERCENTAGE_DIVISOR = new BigDecimal("100");

    private final BigDecimal totalThreshold;
    private final long id;
    private final BigDecimal percentageDiscount;

    public ReductionByPercentageWheTotalExceedsAmountRule(int percentageDiscount, BigDecimal totalThreshold, long id) {
        this.totalThreshold = totalThreshold;
        this.id = id;
        this.percentageDiscount = new BigDecimal(percentageDiscount);
    }

    @Override
    public void addProductItem(BillContext billContext, Product itemProduct) {
        billContext.addNewTotalBillAdjustmentFunction(this, initialAmount -> initialAmount.compareTo(totalThreshold) >= 0 ? initialAmount.subtract(getDiscountedPercentageOfBill(initialAmount)) : initialAmount);
    }

    @Override
    public Long getPromotionalRuleId() {
        return id;
    }

    private BigDecimal getDiscountedPercentageOfBill(BigDecimal initialAmount) {
        return initialAmount.divide(PERCENTAGE_DIVISOR, 4, RoundingMode.HALF_UP).multiply(percentageDiscount);
    }
}
