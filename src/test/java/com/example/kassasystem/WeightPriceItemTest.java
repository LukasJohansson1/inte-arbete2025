package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

public class WeightPriceItemTest {
    
    @ParameterizedTest
    @ValueSource(ints = {1, 10, Integer.MAX_VALUE})
    public void testWeightPriceItemConstructor(int weight) {
        Money pricePerWeightUnit = new Money(1);
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, pricePerWeightUnit, weight);

        assertAll("test that all values are set properly", 
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(weight, item.getWeight()),
            () -> assertEquals(pricePerWeightUnit, item.getPricePerWeightUnit())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100, Integer.MIN_VALUE})
    public void testWeightPriceItemConstructor_shouldThrowException(int weight) {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeightPriceItem("name", SalesTax.MEDIUM, new Money(1), weight);
        });
    }

    @Test
    public void testWeightPriceItemConstructor_throwsException_whenMoneyIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeightPriceItem("name", SalesTax.MEDIUM, null, 1);
        });
    }


}
