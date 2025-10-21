package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    private Receipt receipt;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void testAddInvalidAmountPriceItemShouldThrow(int amount) {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            receipt.addItem(null);
        }, "testAddInvalidAmountPriceItemShouldThrow misslyckades");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void testAddInvalidWeightPriceItemShouldThrow(int weight) {
        WeightPriceItem item = new WeightPriceItem("Banan", SalesTax.LOW, new Money(50), 1500);

        assertThrows(IllegalArgumentException.class, () -> {
            receipt.addItem(null);
        }, "testAddInvalidWeightPriceItemShouldThrow misslyckades");
    }

    @Test
    void testRemoveItemShouldThrowIfItemNotPresent() {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            receipt.removeItem(item);
        }, "testRemoveItemShouldThrowIfItemNotPresent misslyckades");
    }

    @Test
    void testGetTotalShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> {
            receipt.getTotal(); 
        }, "testGetTotalShouldThrowIfEmpty misslyckades");
    }

    @Test
    void testPrintReceiptShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> {
            receipt.printReceipt(); 
        }, "testPrintReceiptShouldThrowIfEmpty misslyckades ");
    }
}
