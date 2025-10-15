package com.example.kassasystem;

public class WeightPriceItem extends Item {
    
    private Money pricePerWeightUnit;
    private int weightInGrams;

    public WeightPriceItem(String name, SalesTax salesTax, Money pricePerWeightUnit, int weightInGrams) {
        super(name, salesTax);

        if (weightInGrams < 1) {
            throw new IllegalArgumentException("Weight must be above 0 grams");
        }

        this.weightInGrams = weightInGrams;
        this.pricePerWeightUnit = pricePerWeightUnit;
    }

    public Money getPricePerWeightUnit() {
        return pricePerWeightUnit;
    }

    public double getWeight() {
        return weightInGrams;
    }

}
