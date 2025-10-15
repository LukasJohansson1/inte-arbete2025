package com.example.kassasystem;

public class WeightPriceItem extends Item {
    
    public WeightPriceItem(String name, SalesTax salesTax, Money pricePerWeightUnit, double weight) {
        super(name, salesTax);
    }

    public Money getPrice() {
        return null;
    }

    public double getWeight() {
        return 0;
    }

}
