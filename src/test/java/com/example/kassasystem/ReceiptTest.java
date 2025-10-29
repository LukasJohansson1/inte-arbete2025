package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void testGetTotalWithAmountPriceItemParameterized(int amount) {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, amount, new EANBarcode("4006381333931"));
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(100 * amount, total.getAmount(), "Total for AmountPriceItem is wrong");
    } // Check that total is calculated correctly for AmountPriceItem

    @Test
    void testCalculateItemTotalWithWeightPriceItem() {
        WeightPriceItem item = new WeightPriceItem("Cheese", SalesTax.LOW, new Money(10), 250, new EANBarcode("4006381333931"));
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(10L * 250, total.getAmount(), "Total for WeightPriceItem should be calculated correctly");
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 1500})
    void testGetTotalWithWeightPriceItem(int weight) {
        WeightPriceItem item = new WeightPriceItem("Banana", SalesTax.LOW, new Money(2), weight, new EANBarcode("4006381333931"));
        receipt.addItem(item);

        Money total = receipt.getTotal();
        assertEquals(2L* weight, total.getAmount(), "Total for WeightPriceItem is wrong");
    } // Check that total is calculated correctly for WeightPriceItem

    @Test
    void testGetTotalWithMultipleItems() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2, new EANBarcode("4006381333931"));
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500, new EANBarcode("5012345678900"));
        receipt.addItem(milk);
        receipt.addItem(banana);

        Money total = receipt.getTotal();
        assertEquals(1700, total.getAmount(), "Total for multiple items is wrong");
    } // Check that total is calculated correctly for multiple items

    @Test
    void testAddItemAddsCorrectly() {
        AmountPriceItem item = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2, new EANBarcode("4006381333931"));
        receipt.addItem(item);
        assertTrue(receipt.getItems().contains(item), "Item should exist in receipt after addItem");
    } // Verify that addItem actually adds the item to the receipt

    @Test
    void testPrintReceiptOutput() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2, new EANBarcode("4006381333931"));
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500, new EANBarcode("5012345678900"));
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
    public void testSortItemsByName() {
        AmountPriceItem itemA = new AmountPriceItem("Apple", SalesTax.MEDIUM, new Money(100), 0, 1, new EANBarcode("4006381333931"));
        AmountPriceItem itemC = new AmountPriceItem("Carrot", SalesTax.MEDIUM, new Money(150), 0, 1, new EANBarcode("5012345678900"));
        AmountPriceItem itemB = new AmountPriceItem("Banana", SalesTax.MEDIUM, new Money(120), 0, 1, new EANBarcode("96385074"));

        receipt.addItem(itemC);
        receipt.addItem(itemA);
        receipt.addItem(itemB);

        receipt.sortItemsByName();
        var items = receipt.getItems();

        assertEquals("Apple", items.get(0).getName());
        assertEquals("Banana", items.get(1).getName());
        assertEquals("Carrot", items.get(2).getName());
    } // Verify that items are sorted correctly by name

    @Test
    public void testSortItemsByPrice() {
        AmountPriceItem milk = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 2, new EANBarcode("4006381333931")); // 200
        AmountPriceItem apple = new AmountPriceItem("Apple", SalesTax.LOW, new Money(50), 0, 3, new EANBarcode("5012345678900"));  // 150
        WeightPriceItem banana = new WeightPriceItem("Banana", SalesTax.LOW, new Money(3), 500, new EANBarcode("96385074")); // 1500

        receipt.addItem(milk);
        receipt.addItem(apple);
        receipt.addItem(banana);

        receipt.sortItemsByPrice();

        List<Item> sortedItems = receipt.getItems();
        assertEquals("Apple", sortedItems.get(0).getName());   // 150
        assertEquals("Milk", sortedItems.get(1).getName());    // 200
        assertEquals("Banana", sortedItems.get(2).getName());  // 1500
    } // Verify that items are sorted correctly by price

    @Test
    public void testCalculateItemTotalOverflowShouldThrow() {
        AmountPriceItem item = new AmountPriceItem(
            "ExpensiveItem", SalesTax.MEDIUM, new Money(Long.MAX_VALUE), 0, 2, new EANBarcode("4006381333931")
        );
        receipt.addItem(item);

        assertThrows(ArithmeticException.class, () -> receipt.getTotal(),
                "Expected ArithmeticException due to overflow when calculating item total");
    } // Check that overflow during item total calculation throws exception


    @Test
    public void testUnknownItemReturnsZero() {
        Item unknownItem = new Item("Unknown", SalesTax.MEDIUM, new EANBarcode("4006381333931")) {};
        receipt.addItem(unknownItem);

        Money total = receipt.getTotal();
        assertEquals(0, total.getAmount(), "Total should be 0 for unknown item type");
    } // Ensure that unknown item types contribute 0 to the total

    @Test
    void testRemoveItemScenarios() {
        // Removing from empty receipt
        AmountPriceItem validItem = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 1, new EANBarcode("4006381333931"));
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(validItem), "Removing from empty receipt should throw");
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(null), "Removing null item should throw");

        // Removing non-existing item
        receipt.addItem(new AmountPriceItem("Bread", SalesTax.MEDIUM, new Money(50), 0, 1, new EANBarcode("5012345678900")));
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(validItem), "Removing non-existing item should throw");

        // Valid removal
        receipt.addItem(validItem);
        assertTrue(receipt.getItems().contains(validItem));
        receipt.removeItem(validItem);
        assertFalse(receipt.getItems().contains(validItem), "Item should be removed successfully");

        // Removing different instance with same data
        AmountPriceItem sameDataDifferentInstance = new AmountPriceItem("Milk", SalesTax.MEDIUM, new Money(100), 0, 1, new EANBarcode("4006381333931"));
        receipt.addItem(validItem);
        assertThrows(IllegalArgumentException.class, () -> receipt.removeItem(sameDataDifferentInstance), "Removing different instance should throw");

        // Multiple instances
        receipt.addItem(validItem);
        receipt.addItem(validItem);
        assertEquals(3, receipt.getItems().stream().filter(i -> i == validItem).count(), "There should be 2 instances");
        receipt.removeItem(validItem);
        assertEquals(2, receipt.getItems().stream().filter(i -> i == validItem).count(), "Only one instance should be removed");
    } // Comprehensive test for various removeItem scenarios

}
