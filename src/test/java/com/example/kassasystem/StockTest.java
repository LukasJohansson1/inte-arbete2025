package com.example.kassasystem;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class StockTest {

    @Test
    public void testStockConstructor_LoadsItems(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        List<Item> items = stock.getItems();
        assertEquals(2, items.size());
        assertEquals("Product A", items.getFirst().getName());
        assertEquals("Product B", items.get(1).getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testStockConstructor_InvalidFilePath_shouldThrowError() {
        assertThrows(RuntimeException.class, () -> new Stock("invalid_path.csv"));
    }

    @Test
    public void testStockConstructor_EmptyFile(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "emptyDataTest", ".csv");
        Stock stock = new Stock(tempFile.toString());

        List<Item> items = stock.getItems();
        assertEquals(0, items.size());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testStockConstructor_MalformedData(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "malformedDataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,invalid_price,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        assertThrows(RuntimeException.class, () -> new Stock(tempFile.toString()));

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testStockAddWeightedItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.addWeightedItem(new WeightPriceItem("Product B", SalesTax.LOW, new Money(549000), 100, new EANBarcode("4006381333931")));
        stock.addWeightedItem(new WeightPriceItem("Product C", SalesTax.LOW, new Money(554000), 200, new EANBarcode("73513537")));
        List<Item> items = stock.getItems();
        assertEquals(3, items.size());
        assertEquals("Product B", items.get(1).getName());
        assertEquals("Product C", items.get(2).getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testStockAddAmountItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        AmountPriceItem item = new AmountPriceItem("Product B", SalesTax.MEDIUM, new Money(299900), 18, 50, new EANBarcode("4006381333931"));
        stock.addAmountItem(item);

        List<Item> items = stock.getItems();
        assertEquals(2, items.size());
        assertEquals("Product B", items.get(1).getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testDeleteItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.deleteItem(new EANBarcode("4006381333931"));
        List<Item> items = stock.getItems();
        assertEquals(1, items.size());
        assertEquals("Product A", items.getFirst().getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testGetSpecificAmountPriceItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        AmountPriceItem item = stock.getAmountItemByBarcode(new EANBarcode("4006381333931"));
        assertEquals("Product B", item.getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testGetSpecificAmountItem_NotFound(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        assertThrows(IllegalArgumentException.class, () -> {
            stock.getAmountItemByBarcode(new EANBarcode("4006381333931"));
        });

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }
    @Test
    public void testGetSpecificWeightPriceItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        WeightPriceItem item = stock.getWeightItemByBarcode(new EANBarcode("5012345678900"));
        assertEquals("Product A", item.getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    public void testGetSpecificWeightItem_NotFound(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());
        assertThrows(IllegalArgumentException.class, () -> {
            stock.getWeightItemByBarcode(new EANBarcode("4006381333931"));
        });

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

}
