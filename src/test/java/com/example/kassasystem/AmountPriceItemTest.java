package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AmountPriceItemTest {
    
    @ParameterizedTest
    @CsvSource({"1, 0", "10, 15", "100, 18"})
    public void testAmountPriceItemConstructor(int amount, int ageLimit) {
        Money price = new Money(1);
        AmountPriceItem item = new AmountPriceItem("name", SalesTax.MEDIUM, price, ageLimit, amount);

        assertAll("test that all values are set properly",
            () -> assertEquals("name", item.getName()),
            () -> assertEquals(SalesTax.MEDIUM, item.getSalesTax()),
            () -> assertEquals(price, item.getPrice()),
            () -> assertEquals(ageLimit, item.getAgeLimit())
        );
    }

    @ParameterizedTest
    @CsvSource({"0, 1", "-1, 1", "2, -1", "2, -10"})
    public void testAmountPriceItemConstructor_shouldThrowException(int amount, int ageLimit) {
        Money price = new Money(1);
        assertThrows(IllegalArgumentException.class, () -> {
            new AmountPriceItem("name", SalesTax.MEDIUM, price, ageLimit, amount);
        });
    }


}
