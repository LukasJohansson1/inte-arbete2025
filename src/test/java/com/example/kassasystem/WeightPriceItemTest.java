package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

class WeightPriceItemTest {
    
    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE})
    void testWeightPriceItemConstructor(int weight) {
        Money pricePerWeightUnit = new Money(1);
        EANBarcode barcode = new EANBarcode("4006381333931");
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, pricePerWeightUnit, weight, barcode);

        assertAll("test that all values are set properly", 
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(weight, item.getWeightInGrams()),
            () -> assertEquals(pricePerWeightUnit, item.getPricePerWeightUnit()),
            () -> assertEquals(barcode, item.getBarcode())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, Integer.MIN_VALUE})
    void testWeightPriceItemConstructor_shouldThrowException(int weight) {
        Money money = new Money(1);
        EANBarcode barcode = new EANBarcode("4006381333931");

        assertThrows(IllegalArgumentException.class, () -> {
            new WeightPriceItem("name", SalesTax.MEDIUM, money, weight, barcode);
        });
    }

    @Test
    void testWeightPriceItemConstructor_throwsException_whenMoneyIsNull() {
        EANBarcode barcode = new EANBarcode("4006381333931");
        assertThrows(IllegalArgumentException.class, () -> {
            new WeightPriceItem("name", SalesTax.MEDIUM, null, 1, barcode);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100})
    void testIncreaseWeightInGrams_validValues_shouldIncrease(int inc) {
        int startWeight = 5;
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), startWeight, new EANBarcode("4006381333931"));

        item.increaseWeightInGrams(inc);

        assertEquals(startWeight + inc, item.getWeightInGrams());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void testIncreaseWeightInGrams_invalidValues_shouldThrow(int inc) {
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), 5, new EANBarcode("4006381333931"));

        assertThrows(IllegalArgumentException.class, () -> item.increaseWeightInGrams(inc));
    }

    @ParameterizedTest
    @CsvSource({"2147483640,10", "2147483647,1"})
    void testIncreaseWeightInGrams_overflow_shouldThrow_andNotChange(int startWeight, int inc) {
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), startWeight, new EANBarcode("4006381333931"));

        assertThrows(ArithmeticException.class, () -> item.increaseWeightInGrams(inc));
        assertEquals(startWeight, item.getWeightInGrams());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    void testDecreaseWeightInGrams_validValues_shouldDecrease(int dec) {
        int startWeight = 10;
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), startWeight, new EANBarcode("4006381333931"));

        item.decreaseWeightInGrams(dec);

        assertEquals(startWeight - dec, item.getWeightInGrams());
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 2147483647})
    void testDecreaseWeightInGrams_tooLarge_shouldThrow_andNotChange(int dec) {
        int startWeight = 10;
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), startWeight, new EANBarcode("4006381333931"));

        assertThrows(IllegalStateException.class, () -> item.decreaseWeightInGrams(dec));
        assertEquals(10, item.getWeightInGrams());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void testDecreaseWeightInGrams_invalidValues_shouldThrow(int dec) {
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), 10, new EANBarcode("4006381333931"));

        assertThrows(IllegalArgumentException.class, () -> item.decreaseWeightInGrams(dec));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 2147483647})
    void testSetWeightInGrams_validValues_shouldSet(int newWeight) {
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), 10, new EANBarcode("4006381333931"));

        item.setWeightInGrams(newWeight);

        assertEquals(newWeight, item.getWeightInGrams());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -2147483648})
    void testSetWeightInGrams_negativeValues_shouldThrow_andNotChange(int newWeight) {
        int startWeight = 10;
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, new Money(0), startWeight, new EANBarcode("4006381333931"));

        assertThrows(IllegalArgumentException.class, () -> item.setWeightInGrams(newWeight));
        assertEquals(startWeight, item.getWeightInGrams());
    }
}
