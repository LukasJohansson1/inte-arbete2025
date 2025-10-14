package com.example.kassasystem;
public class Money {

    // I minsta enheten av valutan, t.ex. öre
    private long amount;

    public Money(long amount){
        if (amount < 0){
            throw new IllegalStateException("Amount must be positive");
        }
        this.amount = amount;
    }

    public long getAmount(){
        return amount;
    }

    public void setAmount(long newAmount){
        if (newAmount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = newAmount;
    }

    public void add(Money other){
        if (this.amount + other.getAmount() < 0){
            throw new ArithmeticException("Possible overflow");
        }
        this.amount = amount + other.getAmount();
    }

    public void subtract(Money other){
        this.amount = amount - other.getAmount();
    }

    // Använder rest och division för att få fram stora och små enheter
    public String toString(){
        long majorUnits = amount / 100;
        long minorUnits = amount % 100;

        return "" + majorUnits + (minorUnits < 10 ? ".0" : ".") + minorUnits + " SEK";
    }

}
