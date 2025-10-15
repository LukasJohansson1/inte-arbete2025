package com.example.kassasystem;

public class AmountPriceItem extends Item {
    
    private Money pricePerUnit;
    private int ageLimit;
    private int amount;

    public AmountPriceItem(String name, SalesTax salesTax, Money pricePerUnit, int ageLimit, int amount) {
        super(name, salesTax);

        if (ageLimit < 0) {
            throw new IllegalArgumentException("Age limit can not be negative");
        }

        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be more than 0");
        }

        this.pricePerUnit = pricePerUnit;
        this.ageLimit = ageLimit;
        this.amount = amount;
    }

    public Money getPrice() {
        return pricePerUnit;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public int getAmount() {
        return amount;
    }
}
