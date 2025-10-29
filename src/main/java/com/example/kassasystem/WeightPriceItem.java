package com.example.kassasystem;

public class WeightPriceItem extends Item {
    
    private final Money pricePerWeightUnit;
    private int weightInGrams;

    public WeightPriceItem(String name, SalesTax salesTax, Money pricePerWeightUnit, int weightInGrams, EANBarcode barcode) { // Constructor
        super(name, salesTax, barcode);

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

    public void increaseWeightInGrams(int i) { // Added method to increase weight
        if (i < 1) {
            throw new IllegalArgumentException("Must increase weight with minimum 1 gram");
        }

        if (weightInGrams + i < 0) {
            throw new ArithmeticException("Increase would cause an overflow");
        }

        weightInGrams += i;
    }

    public void decreaseWeightInGrams(int i) { // Added method to decrease weight
        if (i < 1) {
            throw new IllegalArgumentException("Must decrease weight with minimum 1 gram");
        }

        if (weightInGrams - i < 0) {
            throw new IllegalStateException("Decrease would make weight negative");
        }

        weightInGrams -= i;
    }

    public void setWeightInGrams(int i) { // Added setter for weight to facilitate testing
        if (i < 0) {
            throw new IllegalArgumentException("Can not set weight to negative");
        }

        weightInGrams = i;
    }

}
