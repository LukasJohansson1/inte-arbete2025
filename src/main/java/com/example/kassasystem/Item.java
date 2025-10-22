package com.example.kassasystem;

public abstract class Item {

    private String name;
    private SalesTax salesTax;

    public Item(String name, SalesTax salesTax) {
        if (name == null) {
            throw new IllegalArgumentException("Name can not be null");
        }

        if (salesTax == null) {
            throw new IllegalArgumentException("Sales tax can not be null");
        }

        this.name = name;
        this.salesTax = salesTax;
    }

    public String getName() {
        return name;
    }

    public SalesTax getSalesTax() {
        return salesTax;
    }


}
