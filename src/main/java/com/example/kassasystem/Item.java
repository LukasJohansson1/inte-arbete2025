package com.example.kassasystem;

public abstract class Item {

    private String name;
    private SalesTax salesTax;
    private EANBarcode barcode;

    public Item(String name, SalesTax salesTax, EANBarcode barcode) {
        if (name == null) {
            throw new IllegalArgumentException("Name can not be null");
        }

        if (salesTax == null) {
            throw new IllegalArgumentException("Sales tax can not be null");
        }

        if (barcode == null) {
            throw new IllegalArgumentException("Barcode can not be null");
        }

        this.name = name;
        this.salesTax = salesTax;
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public SalesTax getSalesTax() {
        return salesTax;
    }

    public EANBarcode getBarcode() {
        return barcode;
    }


}
