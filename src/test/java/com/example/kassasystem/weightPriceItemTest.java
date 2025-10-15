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
    @ValueSource(doubles = {1, 10, Double.MAX_VALUE})
    public void testWeightPriceItemConstructor(double weight) {
        Money price = new Money(1);
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, price, weight);

        assertAll("test that all values are set properly", 
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(weight, item.getWeight()),
            () -> assertEquals(price, item.getPrice())
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -100, Double.MIN_VALUE})
    public void testWeightPriceItemConstructor_shouldThrowException(double weight) {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeightPriceItem("name", SalesTax.MEDIUM, new Money(1), weight);
        });
    }


}
