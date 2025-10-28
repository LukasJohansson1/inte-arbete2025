package com.example.kassasystem;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class DiscountRegistry {

    private Map<Discount, List<Item>> discounts = new TreeMap<>();

    public void addDiscount(Discount discount) {
        discounts.put(discount, discount.getItems());

    }

    public Map<Discount, List<Item>> getAllDiscounts() {
        return discounts;
    }

    public ArrayList<Discount> getDiscountsForItem(Item item) {
        ArrayList<Discount> result = new ArrayList<>();
        for (Discount d : discounts.keySet()) {
            if (discounts.get(d).contains(item)) {
                result.add(d);
            }

        }
        return result;
    }

    public Money calculateDiscountedPrize(Item item) {
        ArrayList<Discount> applicableDiscounts = getDiscountsForItem(item);
        if (item instanceof AmountPriceItem) {
            long priceResult = ((AmountPriceItem) item).getPrice().getAmount() * ((AmountPriceItem) item).getAmount();
            if (applicableDiscounts.isEmpty()) {
                return new Money(priceResult);
            }
            for (Discount d : applicableDiscounts) {
                if (d.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                    priceResult -= d.getValue();
                } else {
                    priceResult -= (priceResult * d.getValue()) / 100;
                }
            }
            return new Money(priceResult);
        } else  {
            long priceResult = ((WeightPriceItem) item).getPricePerWeightUnit().getAmount()
                    * ((WeightPriceItem) item).getWeightInGrams();
            if (applicableDiscounts.isEmpty()) {
                return new Money(priceResult);
            }
            for (Discount d : applicableDiscounts) {
                if (d.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                    priceResult -= d.getValue();
                } else  {
                    priceResult -= (priceResult * d.getValue()) / 100;
                }
            }
            return new Money(priceResult);
        }


    }

}
