package com.example.kassasystem;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    private Receipt receipt;

    @BeforeEach
    void setUp() {
        receipt = new Receipt();
    }


    @Test
    void testAddNullAmountPriceItemShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> receipt.addItem(null));
    } // We already do check for valid amount the creation of AmountPriceItem

    @Test
    void testAddNullWeightPriceItemShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> receipt.addItem(null));
    } // We already do check for valid weight the creation of WeightPriceItem

    @Test
    void testRemoveItemShouldThrowIfItemNotPresent() {
        AmountPriceItem item = new AmountPriceItem("Mjölk", SalesTax.MEDIUM, new Money(100), 0, 1);
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(item),
                "Expected IllegalArgumentException when removing item not in receipt");
    }


    @Test
    void testGetTotalShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> receipt.getTotal(),
                "Expected IllegalStateException for getTotal on empty receipt");
    }

    @Test
    void testPrintReceiptShouldThrowIfEmpty() {
        assertThrows(IllegalStateException.class, () -> receipt.printReceipt(),
                "Expected IllegalStateException for printReceipt on empty receipt");
    }


    @Test
    void testGetTotalWithAmountPriceItem() {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 3);
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(300, total.getAmount(), "Total for AmountPriceItem is wrong");
    }

    @Test
    void testGetTotalWithWeightPriceItem() {
        WeightPriceItem item = new WeightPriceItem("Banana", SalesTax.LOW, new Money(2), 1500);
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(3000, total.getAmount(), "Total for WeightPriceItem is wrong");
    }

    @Test
    void testGetTotalWithMultipleItems() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2);
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500);
        receipt.addItem(milk);
        receipt.addItem(banana);

        Money total = receipt.getTotal();
        assertEquals(1700, total.getAmount(), "Total for multiple items is wrong");
    }

    @Test
    void testAddItemAddsCorrectly() {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2);
        receipt.addItem(item);
        assertTrue(receipt.getItems().contains(item), "Item should exist in receipt after addItem");
    }

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
        String expectedBanana = "Banana: " + new Money((long)(banana.getPricePerWeightUnit().getAmount() * banana.getWeight()));
        String expectedTotal = "Total: " + receipt.getTotal();

        assertTrue(output.contains(expectedMilk), "Receipt is missing correct output for milk");
        assertTrue(output.contains(expectedBanana), "Receipt is missing correct output for Banan");
        assertTrue(output.contains(expectedTotal), "Receipt is missing correct output for total");
    }
}
