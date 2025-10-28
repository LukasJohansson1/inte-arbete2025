package com.example.kassasystem;


import java.util.*;

public class Discount implements Comparable<Discount> {

    private String name;
    private List<Item> items;
    private DiscountType discountType;
    private int value;

    public Discount(String name, DiscountType discountType, int value, Item... items) {
        this.name = name;
        this.items = Arrays.asList(items);

        if (items.length == 0) {
            throw new IllegalArgumentException("At least one item must be provided for the discount");
        }

        if (discountType == null) {
            throw new IllegalArgumentException("Discount type cannot be null");
        }

        switch (discountType) { //branch coverage saknas för ifall discountType är null, detta kontrolleras ovan.
            case PERCENTILE:
                if (value < 0 || value > 100) {
                    throw new IllegalArgumentException("Percentile discount value must be between 0 and 100");
                }
                this.discountType = discountType;
                this.value = value;
                break;
            case FIXED_AMOUNT:
                if (value < 1 ) {
                    throw new IllegalArgumentException("Fixed amount discount value must be a positive number");                  
                }
                for (Item item : items) {
                    if (item instanceof AmountPriceItem && ((AmountPriceItem) item).getPrice().getAmount() < value) {
                        throw new IllegalArgumentException("Fixed amount discount value cannot exceed item price");                     
                    }
                    else if (item instanceof WeightPriceItem && ((WeightPriceItem) item).getPricePerWeightUnit().getAmount() < value) {
                        throw new IllegalArgumentException("Fixed amount discount value cannot exceed item price per weight unit");                     
                    }
                    else {
                        continue;
                    }

                }
                this.discountType = discountType;
                this.value = value;
                break;

        }
    }

    public String getName() {
        return name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getValue() {
        return value;
    }

    public int compareTo(Discount other) { //Jämför på enum värden så att FIXED_AMOUNT kommer före PERCENTILE
        return this.discountType.compareTo(other.discountType);
    }


    

}
