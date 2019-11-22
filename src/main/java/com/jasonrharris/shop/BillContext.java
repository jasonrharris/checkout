package com.jasonrharris.shop;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

public class BillContext {
    private final Multimap<Long, BigDecimal> currentPricePerItem = LinkedListMultimap.create();
    private final HashMap<Long,Function<BigDecimal, BigDecimal>> totalBillAdjustersByPromotionRuleId = new HashMap<>();

    public BillContext addProductItem(Product latestProduct) {
        currentPricePerItem.put(latestProduct.getCode(), latestProduct.getPrice());
        return this;
    }

    public void addNewTotalBillAdjustmentFunction(PromotionRule promotionalRule, Function<BigDecimal, BigDecimal> totalAdjuster) {
        if (totalBillAdjustersByPromotionRuleId.containsKey(promotionalRule.getPromotionalRuleId()))
        {
            return;
        }
        totalBillAdjustersByPromotionRuleId.put(promotionalRule.getPromotionalRuleId(), totalAdjuster);
    }

    void modifyPrices(Product product, BigDecimal newPrice) {
        List<BigDecimal> repeatedPrices = new ArrayList<>();
        for (int i = 0; i < getProductCount(product); i++) {
            repeatedPrices.add(newPrice);
        }
        currentPricePerItem.replaceValues(product.getCode(), repeatedPrices);
    }

    int getProductCount(Product product) {
        return currentPricePerItem.get(product.getCode()).size();
    }

    BigDecimal buildTotal() {
        if (currentPricePerItem.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Optional<BigDecimal> optSumOfAllItems = currentPricePerItem.entries().stream().map(Map.Entry::getValue).reduce(BigDecimal::add);
        BigDecimal sumOfAllItems = optSumOfAllItems.orElseThrow(() -> new IllegalArgumentException("The sum of all prices cannot be 'null'"));

        for (Function<BigDecimal, BigDecimal> totalBillAdjuster : totalBillAdjustersByPromotionRuleId.values()) {
            sumOfAllItems = totalBillAdjuster.apply(sumOfAllItems);
        }

        return sumOfAllItems.setScale(2, RoundingMode.HALF_UP);
    }


}
