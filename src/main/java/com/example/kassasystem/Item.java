package com.example.kassasystem;

public abstract class Item {

    private String name;
    private SalesTax salesTax;

    public Item(String name, SalesTax salesTax) {
        this.name = name;
        this.salesTax = salesTax;
    }

    public String getName() {
        return name;
    }

    public SalesTax getSalesTax() {
        return salesTax;
    }

    public abstract Money getTotalPrice();

}
