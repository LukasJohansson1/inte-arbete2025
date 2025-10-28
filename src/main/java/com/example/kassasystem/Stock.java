package com.example.kassasystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<Item> itemList = new ArrayList<>();
    private final String filePath;


    public Stock(String filePath) {
        this.filePath = filePath;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[6].equals("WeightItem")) {
                    EANBarcode barcode = new EANBarcode(parts[0].trim());
                    String name = parts[1].trim();
                    SalesTax salesTax = SalesTax.valueOf(parts[2].trim().toUpperCase());
                    Money pricePerWeightUnit = new Money(Integer.parseInt(parts[3].trim()));
                    int weightInGrams = Integer.parseInt(parts[5].trim());
                    WeightPriceItem item = new WeightPriceItem(name, salesTax, pricePerWeightUnit, weightInGrams, barcode);
                    itemList.add(item);
                } else if (parts[6].equals("AmountItem")) {
                    EANBarcode barcode = new EANBarcode(parts[0].trim());
                    String name = parts[1].trim();
                    SalesTax salesTax = SalesTax.valueOf(parts[2].trim().toUpperCase());
                    Money pricePerUnit = new Money(Integer.parseInt(parts[3].trim()));
                    int ageLimit = Integer.parseInt(parts[4].trim());
                    int amount = Integer.parseInt(parts[5].trim());
                    AmountPriceItem item = new AmountPriceItem(name, salesTax, pricePerUnit, ageLimit, amount, barcode);
                    itemList.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading stock file: " + e.getMessage());
        }

    }
    public List<Item> getItems() {

        return itemList;
    }

    public void addWeightedItem(WeightPriceItem item) throws IOException {
        Path pathOfFile = Path.of(this.filePath);
        String data = String.format("%n%s,%s,%s,%s,%d,%d,%s", item.getBarcode(), item.getName(), item.getSalesTax(), item.getPricePerWeightUnit(), 0, item.getWeightInGrams(), "WeightItem");
        Files.writeString(pathOfFile, data);
        itemList.add(item);
    }

    public void addAmountItem(AmountPriceItem item) throws IOException {
        Path pathOfFile = Path.of(this.filePath);
        String data = String.format("%n%s,%s,%s,%s,%d,%d,%s", item.getBarcode(), item.getName(), item.getSalesTax(), item.getPrice(), item.getAgeLimit(), item.getAmount(), "AmountItem");
        Files.writeString(pathOfFile, data);
        itemList.add(item);
    }

    public void deleteItem(EANBarcode barcode) throws IOException {
        Item itemToDelete = null;
        for (Item item : itemList) {
            if (item.getBarcode().equals(barcode)) {
                itemToDelete = item;
                break;
            }
        }
        if (itemToDelete != null) {
            itemList.remove(itemToDelete);
            Path pathOfFile = Path.of(this.filePath);
            List<String> lines = Files.readAllLines(pathOfFile);
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                if (!line.startsWith(barcode.toString())) {
                    updatedLines.add(line);
                }
            }
            Files.write(pathOfFile, updatedLines);
        }
    }

    public AmountPriceItem getAmountItemByBarcode(EANBarcode barcode) {
        for (Item item : itemList) {
            if (item instanceof AmountPriceItem && item.getBarcode().equals(barcode)) {
                return (AmountPriceItem) item;
            }
        }
        throw new IllegalArgumentException("No AmountPriceItem found with the given barcode");
    }

    public WeightPriceItem getWeightItemByBarcode(EANBarcode barcode) {
        for (Item item : itemList) {
            if (item instanceof WeightPriceItem && item.getBarcode().equals(barcode)) {
                return (WeightPriceItem) item;
            }
        }
        throw new IllegalArgumentException("No AmountPriceItem found with the given barcode");
    }
}
