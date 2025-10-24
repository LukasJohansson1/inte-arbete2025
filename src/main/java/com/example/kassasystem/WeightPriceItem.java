package com.example.kassasystem;

public class WeightPriceItem extends Item {
    
    private Money pricePerWeightUnit;
    private int weightInGrams;

    public WeightPriceItem(String name, SalesTax salesTax, Money pricePerWeightUnit, int weightInGrams) {
        super(name, salesTax);

        if (weightInGrams < 1) {
            throw new IllegalArgumentException("Weight must be above 0 grams");
        }

        if (pricePerWeightUnit == null) {
            throw new IllegalArgumentException("Price per weight unit can not be null");
        }

        this.weightInGrams = weightInGrams;
        this.pricePerWeightUnit = pricePerWeightUnit;
    }

    public Money getPricePerWeightUnit() {
        return pricePerWeightUnit;
    }

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public void increaseWeight(int i) {

    }

    public void decreaseWeight(int i) {

    }

    public void setWeight(int i) {

    }

}
