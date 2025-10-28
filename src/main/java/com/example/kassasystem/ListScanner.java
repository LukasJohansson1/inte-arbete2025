package com.example.kassasystem;

import java.util.*;

public class ListScanner implements Scanner {

    private final List<String> listToScan;
    private final Stock stock;

    public ListScanner(List<String> listToScan, Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Stock can not be null");
        }

        if (listToScan == null) {
            throw new IllegalArgumentException("List to scan can not be null");
        }

        this.listToScan = listToScan;
        this.stock = stock;
    }

    @Override
    public Item scan() {
        validateScan();
        return findItemWithBarcode(validateBarcode());
    }

    private void validateScan() {
        if (listToScan.isEmpty()) {
            throw new IllegalArgumentException("Scan failed");
        }
        if (listToScan.getFirst() == null) {
            listToScan.removeFirst();
            throw new IllegalArgumentException("Scan failed");
        }
    }

    private EANBarcode validateBarcode() {
        try {
            return new EANBarcode(listToScan.getFirst());
        } catch (Exception e) {
            listToScan.removeFirst();
            throw new IllegalArgumentException("The scanned input is not a valid barcode");
        }
    }

    private Item findItemWithBarcode(EANBarcode barcode) {
        try {
            Item item = stock.getSpecificItemByBarcode(barcode);
            listToScan.removeFirst();
            return item;
        } catch (IllegalArgumentException e) {
            listToScan.removeFirst();
            throw new NoSuchElementException("The item with the barcode does not exist in the stock database");
        }
    }

}
