package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

class AmountPriceItemTest {
    
    @ParameterizedTest
    @CsvSource({"1, 0", "10, 15", "100, 18"})
    void testAmountPriceItemConstructor(int amount, int ageLimit) {
        Money price = new Money(1);
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, price, ageLimit, amount, barcode);

        assertAll("test that all values are set properly",
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(price, item.getPrice()),
            () -> assertEquals(ageLimit, item.getAgeLimit()),
            () -> assertEquals(barcode, item.getBarcode())
        );
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "-1, 1", "2, -1", "2, -10"})
    void testAmountPriceItemConstructor_shouldThrowException(int amount, int ageLimit) {
        Money price = new Money(1);
        EANBarcode barcode = new EANBarcode("4006381333931");
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem("name", SalesTax.MEDIUM, price, ageLimit, amount, barcode);
        });
    }

    @Test
    void testAmountPriceItemConstructor_throwsException_whenNameIsNull() {
        EANBarcode barcode = new EANBarcode("4006381333931");
        Money money = new Money(0);
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem(null, SalesTax.MEDIUM, money, 0, 1, barcode);
        });
    }

    @Test
    void testAmountPriceItemConstructor_throwsException_whenSalesTaxIsNull() {
        EANBarcode barcode = new EANBarcode("4006381333931");
        Money money = new Money(0);
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem("name", null, money, 0, 1, barcode);
        });
    }

    @Test
    void testAmountPriceItemConstructor_throwsException_whenMoneyIsNull() {
        EANBarcode barcode = new EANBarcode("4006381333931");
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem("name", SalesTax.MEDIUM, null, 0, 1, barcode);
        });
    }

    @Test
    void testAmountPriceItemConstructor_throwsException_whenBarcodeIsNull() {
        Money money = new Money(0);
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem("name", SalesTax.MEDIUM, money, 0, 1, null);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE-1})
    void testIncreaseAmount(int increaseAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("4006381333931"));
        item.increaseAmount(increaseAmount);
        assertEquals(increaseAmount + 1, item.getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, Integer.MIN_VALUE})
    void testIncreaseAmount_throwsException_ifValueIsZeroOrNegative(int increaseAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("4006381333931"));
        assertThrows(IllegalArgumentException.class, () -> {
            item.increaseAmount(increaseAmount);
        });
    }

    @Test
    void testIncreaseAmount_throwsException_ifOverflow() {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, Integer.MAX_VALUE, new EANBarcode("4006381333931"));
        assertThrows(ArithmeticException.class, () -> {
            item.increaseAmount(1);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE})
    void testDecreaseAmount(int decreaseAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, Integer.MAX_VALUE, new EANBarcode("4006381333931"));
        item.decreaseAmount(decreaseAmount);
        assertEquals(Integer.MAX_VALUE - decreaseAmount, item.getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, Integer.MIN_VALUE})
    void testDecreaseAmount_throwsException_ifValueIsZeroOrNegative(int decreaseAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("4006381333931"));
        assertThrows(IllegalArgumentException.class, () -> {
            item.decreaseAmount(decreaseAmount);
        });
    }

    @Test
    void testDecreaseAmount_throwsException_ifAmountIsNegative() {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("4006381333931"));
        assertThrows(IllegalStateException.class, () -> {
            item.decreaseAmount(2);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, Integer.MAX_VALUE})
    void testSetAmount(int setAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 2, new EANBarcode("4006381333931"));
        item.setAmount(setAmount);
        assertEquals(setAmount, item.getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, Integer.MIN_VALUE})
    void testSetAmount_throwsException_ifAmountIsNegative(int setAmount) {
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, new Money(0), 0, 1, new EANBarcode("4006381333931"));
        assertThrows(IllegalArgumentException.class, () -> {
            item.setAmount(setAmount);
        });
    }






}
