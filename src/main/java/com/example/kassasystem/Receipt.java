package com.example.kassasystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Låter co-pilot hantera alla kommentarer

public class Receipt {

    private final List<Item> items;

    public Receipt() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) { // Add item to receipt
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        items.add(item);
    }

    public void removeItem(Item item) { // Remove item from receipt
        if(item == null || !items.remove(item)) {
            throw new IllegalArgumentException("Item not found in receipt");
        }
    }

    public Money getTotal() { // Calculate total price of all items
        Money total = new Money(0); // Empty receipt total
        for (Item item : items) {
            total.add(calculateItemTotal(item));
        }
        return total;
    }

    public void printReceipt() { // Print receipt details
        StringBuilder sb = new StringBuilder();
        sb.append("----- Receipt -----\n");

        for (Item item : items) {
            Money itemTotal = calculateItemTotal(item);
            sb.append(item.getName())
            .append(": ")
            .append(itemTotal)
            .append("\n");
        }

        sb.append("Total: ").append(getTotal()).append("\n");

        System.out.print(sb.toString());
    }

    public List<Item> getItems() { // Get unmodifiable list of items
        return Collections.unmodifiableList(items);
    }

    public void sortItemsByName() { // Sort items by their name
        items.sort(Comparator.comparing(Item::getName));
    }

    public void sortItemsByPrice() { // Sort items by their total price
        items.sort((item1, item2) -> {
            long price1 = calculateItemTotal(item1).getAmount();
            long price2 = calculateItemTotal(item2).getAmount();
            return Long.compare(price1, price2);
        });
    }


    private Money calculateItemTotal(Item item) { // Calculate total price for a single item
        try {
            if (item instanceof AmountPriceItem amountItem) {
                return new Money(Math.multiplyExact(amountItem.getPrice().getAmount(), amountItem.getAmount()));
            } else if (item instanceof WeightPriceItem weightPriceItem) {
                return new Money(Math.multiplyExact(weightPriceItem.getPricePerWeightUnit().getAmount(), (long) weightPriceItem.getWeightInGrams()));
            }
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow occurred when calculating item total: " + item.getName());
        }
        return new Money(0);
    }

}
