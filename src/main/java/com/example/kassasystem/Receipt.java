package com.example.kassasystem;

import java.util.List;

public class Receipt {
    private List<Item> items;

    public Receipt(List<Item> items) {
        this.items = items;
    }

    public void printReceipt() {
        System.out.println("=== KVITTO ===");
        for (Item item : items) {
            System.out.println(item.getName() + " " + item.getTotalPrice());
        }
    }

    public Money getTotalPrice() {
        long totalAmount = 0;

        for (Item item : items) {
            if (item instanceof AmountPriceItem) {
                totalAmount += 1;
            } else if (item instanceof WeightPriceItem) {
                totalAmount += 1;
            }
        }

        return new Money(totalAmount);
    }
}
