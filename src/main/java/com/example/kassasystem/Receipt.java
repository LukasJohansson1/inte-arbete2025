package com.example.kassasystem;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private final List<Item> items;

    public Receipt() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        items.add(item);
    }

    public void removeItem(Item item) {
        if(item == null || !items.remove(item)) {
            throw new IllegalArgumentException("Item not found in receipt");
        }
    }

    public Money getTotal() {

        if(items.isEmpty()) {
            throw new IllegalStateException("Receipt is empty");
        }

        long totalAmount = 0;

        for (Item item : items) {
            if (item instanceof AmountPriceItem amountItem) {
                totalAmount += amountItem.getPrice().getAmount() * amountItem.getAmount();
            } else if (item instanceof WeightPriceItem weightPriceItem){
                totalAmount += weightPriceItem.getPricePerWeightUnit().getAmount() * (long) weightPriceItem.getWeightInGrams();
            }
        }
        return new Money(totalAmount);
    }

    public void printReceipt() {
        if(items.isEmpty()) {
            throw new IllegalStateException("Receipt is empty");
        }

        System.out.println("----- Receipt -----");
        for (Item item : items) {
            long itemTotalAmount = 0;
            if(item instanceof AmountPriceItem amountItem) {
                itemTotalAmount = amountItem.getPrice().getAmount() * amountItem.getAmount();
            } else if (item instanceof WeightPriceItem weightPriceItem) {
                itemTotalAmount =  weightPriceItem.getPricePerWeightUnit().getAmount() * (long) weightPriceItem.getWeightInGrams();
            }
            System.out.println(item.getName() + ": " + new Money(itemTotalAmount));
        }
        System.out.println("Total: " + getTotal());
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
