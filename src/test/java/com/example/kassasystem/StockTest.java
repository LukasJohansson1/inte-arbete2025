package com.example.kassasystem;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {

    @Test
    void testStockConstructor_LoadsItems(@TempDir Path tempDir) throws IOException {
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
    void testStockConstructor_InvalidFilePath_shouldThrowError() {
        assertThrows(RuntimeException.class, () -> new Stock("invalid_path.csv"));
    }

    @Test
    void testStockConstructor_EmptyFile(@TempDir Path tempDir) throws IOException {
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
    void testStockConstructor_MalformedData(@TempDir Path tempDir) throws IOException {
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
    void testLoadItems_SkipsBlankAndWhitespaceLines(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "blankLinesTest", ".csv");
        String data = """
            Barcode,name,salesTax,price,ageLimit,weight/amount,type

            
            5012345678900,Product A,HIGH,199900,0,500,WeightItem
            4006381333931,Product B,LOW,549000,12,100,AmountItem
            """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        List<Item> items = stock.getItems();
        assertEquals(2, items.size());
        assertEquals("Product A", items.get(0).getName());
        assertEquals("Product B", items.get(1).getName());
    }

    @Test
    void testLoadItems_SkipsLinesWithLessThanSevenParts(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "blankLinesTest", ".csv");
        String data = """
            Barcode,name,salesTax,price,ageLimit,weight/amount,type
            5012345678900,Product A,HIGH,199900,0,
            4006381333931,Product B,LOW,549000,12,100,AmountItem
            """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        List<Item> items = stock.getItems();
        assertEquals(1, items.size());
        assertEquals("Product B", items.getFirst().getName());
    }

    @Test
    void testStockAddWeightedItem_AddedInList(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.addItem(new WeightPriceItem("Product B", SalesTax.LOW, new Money(549000), 100, new EANBarcode("4006381333931")));
        stock.addItem(new WeightPriceItem("Product C", SalesTax.LOW, new Money(554000), 200, new EANBarcode("73513537")));
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
    void testStockAddAmountItem_AddedInList(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.addItem (new AmountPriceItem("Product B", SalesTax.LOW, new Money(549000), 12, 100, new EANBarcode("4006381333931")));
        stock.addItem (new AmountPriceItem("Product C", SalesTax.LOW, new Money(554000), 15, 200, new EANBarcode("73513537")));

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
    void testStockAddAmountItem_AddedInFile(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.addItem (new AmountPriceItem("Product B", SalesTax.LOW, new Money(549000), 12, 100, new EANBarcode("4006381333931")));

        String fileContents = Files.readString(tempFile);
        System.out.println(fileContents);
        assertTrue(fileContents.contains("4006381333931"));


        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockAddItem_NullItem_shouldThrowException(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        assertThrows(NullPointerException.class, () -> stock.addItem(null));

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockAddItem_DuplicateBarcode_shouldThrowException(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        AmountPriceItem duplicateItem = new AmountPriceItem("Product B", SalesTax.LOW, new Money(549000), 12, 100, new EANBarcode("5012345678900"));

        assertThrows(IllegalArgumentException.class, () -> stock.addItem(duplicateItem));

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockDeleteItem_DeletedInList(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        boolean deletedItem = stock.deleteItem(new EANBarcode("4006381333931"));
        assertTrue(deletedItem);
        List<Item> items = stock.getItems();
        assertEquals(1, items.size());
        assertEquals("Product A", items.getFirst().getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockDeleteItem_DeletedInFile(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                
                
                
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        stock.deleteItem(new EANBarcode("4006381333931"));

        String fileContents = Files.readString(tempFile);
        assertFalse(fileContents.contains("4006381333931"));

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockDeleteItem_NotFound(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        boolean deletedItem = stock.deleteItem(new EANBarcode("4006381333931"));
        assertFalse(deletedItem);
        List<Item> items = stock.getItems();
        assertEquals(1, items.size());
        assertEquals("Product A", items.getFirst().getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockGetSpecificItem(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                4006381333931,Product B,LOW,549000,12,100,AmountItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        Item item = stock.getSpecificItemByBarcode(new EANBarcode("4006381333931"));
        assertEquals("Product B", item.getName());

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }

    @Test
    void testStockGetSpecificItem_NotFound(@TempDir Path tempDir) throws IOException {
        Path tempFile = Files.createTempFile(tempDir, "dataTest", ".csv");
        String data = """
                Barcode,name,salesTax,price,ageLimit,weight/amount,type
                5012345678900,Product A,HIGH,199900,0,500,WeightItem
                """;
        Files.writeString(tempFile, data);
        Stock stock = new Stock(tempFile.toString());

        assertThrows(IllegalArgumentException.class, () -> stock.getSpecificItemByBarcode(new EANBarcode("4006381333931")));

        boolean deleted = Files.deleteIfExists(tempFile);
        if (!deleted) {
            fail("Could not delete temp file: " + tempFile);
        }
    }
}
