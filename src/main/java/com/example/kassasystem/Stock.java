package com.example.kassasystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stock {
    private final List<Item> itemList = new ArrayList<>();
    private final String filePath;


    public Stock(String filePath) {
        this.filePath = filePath;
        loadItemsFromFile();
    }

    private void loadItemsFromFile() {
        Path path = Path.of(filePath);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 7) {
                    continue;
                }
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
        return new ArrayList<>(itemList);
    }

    public void addItem(Item item) throws IOException {
        Path pathOfFile = Path.of(this.filePath);
        String data;
        Objects.requireNonNull(item, "Item cannot be null");
        for (Item existingItem : itemList) {
            if (existingItem.getBarcode().getCode().equals(item.getBarcode().getCode())) {
                throw new IllegalArgumentException("Item with the same barcode already exists");
            }
        }
        if (item instanceof WeightPriceItem) {
            data = String.format("%n%s,%s,%s,%s,%d,%d,%s",
                    item.getBarcode().getCode(),
                    item.getName(),
                    item.getSalesTax(),
                    ((WeightPriceItem) item).getPricePerWeightUnit(),
                    0, ((WeightPriceItem) item).getWeightInGrams(),
                    "WeightItem");
        }
        else {
            data = String.format("%n%s,%s,%s,%s,%d,%d,%s",
                    item.getBarcode().getCode(),
                    item.getName(),
                    item.getSalesTax(),
                    ((AmountPriceItem) item).getPrice(),
                    ((AmountPriceItem) item).getAgeLimit(),
                    ((AmountPriceItem) item).getAmount(),
                    "AmountItem");
        }
        Files.writeString(pathOfFile, data);
        itemList.add(item);
    }

    public boolean deleteItem(EANBarcode barcode) throws IOException {
        // Find the item to delete
        Item itemToDelete = null;
        for (Item item : itemList) {
            if (item.getBarcode().getCode().equals(barcode.getCode())) {
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
                if (line.isBlank()) {
                    continue;
                }
                String firstColumn = line.split(",", -1)[0].trim();
                if (!firstColumn.equals(barcode.getCode())) {
                    updatedLines.add(line);
                }
            }
            Files.write(pathOfFile, updatedLines);
            return true;
        }
        return false;
    }

    public Item getSpecificItemByBarcode(EANBarcode barcode) {
        for (Item item : itemList) {
            if (item.getBarcode().equals(barcode)) {
                return item;
            }
        }
        throw new IllegalArgumentException("No item found with the given barcode");
    }
}
