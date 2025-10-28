package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;

public class ListScannerTest {

    // Testdata
    private static final Item ITEM_1 = new AmountPriceItem("1", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("42853718"));
    private static final Item ITEM_2 = new AmountPriceItem("2", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("53075284"));
    private static final Item ITEM_3 = new AmountPriceItem("3", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("5355637570514"));
    private static final Item ITEM_4 = new WeightPriceItem("4", SalesTax.MEDIUM, new Money(0), 1, new EANBarcode("6463362596385"));
    private static final Item ITEM_5 = new WeightPriceItem("5", SalesTax.MEDIUM, new Money(0), 1, new EANBarcode("1093776181768"));
    private static final Item ITEM_6 = new WeightPriceItem("6", SalesTax.MEDIUM, new Money(0), 1, new EANBarcode("11404194"));

    // Hjälpmetod för att skapa ett lager med items
    private Stock createTestStock() throws IOException {
        Path tempFile = Files.createTempFile("dataTest", ".csv");
        String data = "";
        Files.writeString(tempFile, data);

        Stock stock = new Stock(tempFile.toString());

        stock.addItem(ITEM_1);
        stock.addItem(ITEM_2);
        stock.addItem(ITEM_3);
        stock.addItem(ITEM_4);
        stock.addItem(ITEM_5);
        stock.addItem(ITEM_6);

        Files.deleteIfExists(tempFile);

        return stock;
    }

    @Test
    public void testConstructor_StockNull_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new ListScanner(new ArrayList<>(), null));
    }

    @Test
    public void testConstructor_ListNull_throwsException() throws IOException{
        assertThrows(IllegalArgumentException.class, () -> new ListScanner(null, createTestStock()));
    }

    // För denna skanner räknas en misslyckad scan om listan är tom eller objektet är null
    @Test
    public void testScan_FailedScanning_throwsException() throws IOException {
        List<String> listWithNull = new ArrayList<>();
        listWithNull.add(null);
        ListScanner scanner = new ListScanner(listWithNull, createTestStock());
        assertThrows(IllegalArgumentException.class, () -> scanner.scan());
        assertThrows(IllegalArgumentException.class, () -> scanner.scan());
    }

    @ParameterizedTest
    @ValueSource(strings = {"8901234567891", "asfasf", "23456780", "<>-.,,"})
    public void testScan_invalidBarcode_throwsException(String barcode) throws IOException {
        List<String> listToScan = new ArrayList<>(List.of(barcode));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        assertThrows(IllegalArgumentException.class, () -> scanner.scan());
    }

    @ParameterizedTest
    @ValueSource(strings = {"30927797", "57263168", "8756034956074", "4487212738659"})
    public void testScan_noItemWithBarcodeInStock_throwsException(String barcode) throws IOException {
        List<String> listToScan = new ArrayList<>(List.of(barcode));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        assertThrows(NoSuchElementException.class, () -> scanner.scan());
    }

    @ParameterizedTest
    @ValueSource(strings = {"42853718", "5355637570514", "6463362596385", "11404194"})
    public void testScan_validBarcodes_works(String barcode) throws IOException {
        List<String> listToScan = new ArrayList<>(List.of(barcode));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        assertEquals(scanner.scan().getBarcode(), new EANBarcode(barcode));
    }

    @Test
    public void testScan_NullObject_getRemovedFromList() throws IOException {
        String barcodeString = "42853718";
        List<String> listToScan = new ArrayList<>();
        listToScan.add(null);
        listToScan.add(barcodeString);
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        try {
            scanner.scan();
        } catch (IllegalArgumentException e) {} // Vi bryr oss inte om felet, vi vill bara se vad som händer vid scannen efter
        
        assertEquals(scanner.scan().getBarcode(), new EANBarcode(barcodeString));
    }

    @Test
    public void testScan_invalidBarcode_getRemovedFromList() throws IOException {
        String barcodeString = "42853718";
        List<String> listToScan = new ArrayList<>(List.of("8901234567891", barcodeString));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        try {
            scanner.scan();
        } catch (IllegalArgumentException e) {} // Vi bryr oss inte om felet, vi vill bara se vad som händer vid scannen efter

        assertEquals(scanner.scan().getBarcode(), new EANBarcode(barcodeString));
    }

    @Test
    public void testScan_noItemWithBarcodeInStock_getRemovedFromList() throws IOException {
        String barcodeString = "42853718";
        List<String> listToScan = new ArrayList<>(List.of("30927797", barcodeString));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        try {
            scanner.scan();
        } catch (NoSuchElementException e) {} // Vi bryr oss inte om felet, vi vill bara se vad som händer vid scannen efter

        assertEquals(scanner.scan().getBarcode(), new EANBarcode(barcodeString));
    }

    @Test
    public void testScan_validBarcode_getRemovedFromList() throws IOException {
        String barcodeString = "42853718";
        List<String> listToScan = new ArrayList<>(List.of("53075284", barcodeString));
        ListScanner scanner = new ListScanner(listToScan, createTestStock());
        scanner.scan();
        assertEquals(scanner.scan().getBarcode(), new EANBarcode(barcodeString));
    }
}
