package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeightPriceItemTest {
    
    @ParameterizedTest
    @CsvSource({"1, 2", "10, 1", "100, 200"})
    public void testWeightPriceItemConstructor(double weight, long pricePerWeightUnit) {
        Money price = new Money(pricePerWeightUnit);
        WeightPriceItem item = new WeightPriceItem("name", SalesTax.MEDIUM, price, weight);

        assertAll("name", 
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(weight, item.getWeight())
        );

        // assertEquals("name", item.getName());
        // assertEquals(SalesTax.MEDIUM, item.getSalesTax());
        // assertEquals(weight, item.getWeight());
        // assertEquals(price, item.getPrice());
    }


}
