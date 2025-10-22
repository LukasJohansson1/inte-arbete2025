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

    public void increaseAmount(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("You must increase the amount with a number above 0");
        }

        if ((i + amount) < 0) {
            throw new ArithmeticException("An increase with the given amount would cause an overflow");
        }

        amount += i;
    }

    public void decreaseAmount(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("You must decrease the amount with a number above 0");
        }

        if ((amount - i < 0)) {
            throw new IllegalStateException("The decrease would set amount to negative");
        }

        amount -= i;
    }

    public void setAmount(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("You can't set amount to negative");
        }

        amount = i;
    }
}
