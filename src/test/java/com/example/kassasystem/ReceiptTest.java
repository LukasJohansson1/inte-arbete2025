package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
// Låter co-pilot hantera alla kommentarer

public class ReceiptTest {

    private Receipt receipt;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
    }


    @Test
    void testAddNullAmountPriceItemShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> receipt.addItem(null));
    } // We already do check for valid amount the creation of AmountPriceItem only need to check for null

    @Test
    void testAddNullWeightPriceItemShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> receipt.addItem(null));
    } // We already do check for valid weight the creation of WeightPriceItem only need to check for null

    @Test
    void testRemoveItemShouldThrowIfItemNotPresent() {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(item),
                "Expected IllegalArgumentException when removing item not in receipt");
    } //Check that removing an item not in receipt throws exception


    @Test
    void testGetTotalShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> receipt.getTotal(),
                "Expected IllegalStateException for getTotal on empty receipt");
    } // Ensure that calling getTotal on an empty receipt throws exception

    @Test
    void testPrintReceiptShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> receipt.printReceipt(),
                "Expected IllegalStateException for printReceipt on empty receipt");
    } // Ensure that calling printReceipt on an empty receipt throws exception


    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void testGetTotalWithAmountPriceItemParameterized(int amount) {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, amount);
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(100 * amount, total.getAmount(), "Total for AmountPriceItem is wrong");
    } // Check that total is calculated correctly for AmountPriceItem

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 1500})
    void testGetTotalWithWeightPriceItem(int weight) {
        WeightPriceItem item = new WeightPriceItem("Banana", SalesTax.LOW, new Money(2), weight);
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(2L* weight, total.getAmount(), "Total for WeightPriceItem is wrong");
    } // Check that total is calculated correctly for WeightPriceItem

    @Test
    void testGetTotalWithMultipleItems() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2);
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500);
        receipt.addItem(milk);
        receipt.addItem(banana);

        Money total = receipt.getTotal();
        assertEquals(1700, total.getAmount(), "Total for multiple items is wrong");
    } // Check that total is calculated correctly for multiple items

    @Test
    void testAddItemAddsCorrectly() {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2);
        receipt.addItem(item);
        assertTrue(receipt.getItems().contains(item), "Item should exist in receipt after addItem");
    } // Verify that addItem actually adds the item to the receipt

    @Test
    void testPrintReceiptOutput() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2);
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500);
        receipt.addItem(milk);
        receipt.addItem(banana);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        receipt.printReceipt();

        System.setOut(originalOut);

        String output = outContent.toString();

        String expectedMilk = "Milk: " + new Money(milk.getPrice().getAmount() * milk.getAmount());
        String expectedBanana = "Banana: " + new Money((long)(banana.getPricePerWeightUnit().getAmount() * banana.getWeightInGrams()));
        String expectedTotal = "Total: " + receipt.getTotal();

        assertTrue(output.contains(expectedMilk), "Receipt is missing correct output for milk");
        assertTrue(output.contains(expectedBanana), "Receipt is missing correct output for Banan");
        assertTrue(output.contains(expectedTotal), "Receipt is missing correct output for total");
    } // Check that printReceipt outputs the correct information

    @Test
    void testAddAndRemoveItem(){
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0 , 2);
        receipt.addItem(item);
        assertTrue(receipt.getItems().contains(item), "Item should exist in receipt after addItem");

        receipt.removeItem(item);
        assertFalse(receipt.getItems().contains(item), "Item should not exist in receipt after removeItem");
    } // Verify that addItem and removeItem work correctly in tandem

    @Test
    void testRemoveItemFromEmptyReceipt() {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(item));
    } // Check that removing an item from an empty receipt throws exception

    @Test
    void testRemoveItemWithDifferentInstance() {
        AmountPriceItem item1 = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        AmountPriceItem item2 = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        receipt.addItem(item1);
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(item2));
    }

    @Test
    void testRemoveOneInstanceWhenMultipleExist() {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        receipt.addItem(item);
        receipt.addItem(item);
        assertEquals(2, receipt.getItems().size());
        
        receipt.removeItem(item);
        assertEquals(1, receipt.getItems().size());
    }
}
