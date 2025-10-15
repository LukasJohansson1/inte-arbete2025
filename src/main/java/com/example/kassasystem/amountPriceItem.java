package com.example.kassasystem;

public class AmountPriceItem extends Item {
    
    public AmountPriceItem(String name, SalesTax salesTax, Money pricePerUnit, int ageLimit, int amount) {
        super(name, salesTax);
    }

    public Money getPrice() {
        return null;
    }

    public int getAgeLimit() {
        return 999;
    }

    public int getAmount() {
        return 999;
    }
}
