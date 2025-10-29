package com.example.kassasystem;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;

public class DiscountRegistry {

    private final TreeMap<Discount, List<Item>> discounts = new TreeMap<>();

    public void addDiscount(Discount discount) {
        discounts.put(discount, discount.getItems());

    }

    public TreeMap<Discount, List<Item>> getAllDiscounts() {
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

    public Money calculateDiscountedPrize(Item item) { //Varor kan ha flera rabatter, fixed amount appliceras innan procentrabatter.
        ArrayList<Discount> applicableDiscounts = getDiscountsForItem(item);
        long priceResult;
        if (item instanceof AmountPriceItem amountItem) {
            priceResult = amountItem.getPrice().getAmount() * amountItem.getAmount();
        } else if (item instanceof WeightPriceItem weightItem) {
            priceResult = weightItem.getPricePerWeightUnit().getAmount() * weightItem.getWeightInGrams();
        } else {
            throw new IllegalArgumentException("Unknown item type: " + item.getClass());
        }

        // Apply discounts
        priceResult = applyDiscounts(priceResult, applicableDiscounts);

        return new Money(priceResult);
    }

    private long applyDiscounts(long price, List<Discount> discounts) {
        long result = price;

        for (Discount d : discounts) {
            if (d.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                result -= d.getValue();
            } else {
                result -= result * d.getValue() / 100;
            }
        }

        return result;
    }
}
