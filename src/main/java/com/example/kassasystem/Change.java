package com.example.kassasystem;

public class Change {


    //Returnerar ett nytt money-objekt som representerar växeln
    //Ska även returnera tomt money-objekt ifall paid == price
    public static Money exchangeMoney(Money price, Money paid) {
        if (paid.getAmount() < 1) {
            throw new IllegalArgumentException("Paid amount must be at least 1 unit");
        }
        if (paid.getAmount() < price.getAmount()) {
            throw new IllegalArgumentException("Paid amount is less than the price");
        }

        long changeAmount = paid.getAmount() - price.getAmount();
        return new Money(changeAmount);
    }

}
