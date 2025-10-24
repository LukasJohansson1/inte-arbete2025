package com.example.kassasystem;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;


public class MoneyTest {

    @ParameterizedTest
    @ValueSource(longs = { 0, 1, 100, Long.MAX_VALUE})
    public void testMoneyCreation_ValidAmount(long amount) {
        Money money = new Money(amount);
        assertEquals(amount, money.getAmount());
        
}
    @ParameterizedTest
    @ValueSource(longs = { -1, -100, Long.MIN_VALUE})
    public void testMoneyCreationNegative_ThrowException(long amount) {
        assertThrows(IllegalStateException.class, () -> {
        new Money(amount);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0.00 SEK",
            "1, 0.01 SEK",
            "10, 0.10 SEK",
            "100, 1.00 SEK",
            "10000, 100.00 SEK"})
    public void testMoneyToString(long amount, String expected) {
        Money money = new Money(amount);
        assertEquals(expected, money.toString());

    }

    @ParameterizedTest
    @ValueSource(longs = {0, 100, Long.MAX_VALUE})
    public void testMoneySetAmountPositive_ValidAmount(long amount) {
        Money money = new Money(0);
        money.setAmount(amount);
        assertEquals(amount, money.getAmount());

    }

    @ParameterizedTest
    @ValueSource(longs = {-1, -100, Long.MIN_VALUE})
    public void testMoneySetAmountNegative_InvalidAmount(long amount) {
        Money money = new Money(0);
        assertThrows(IllegalArgumentException.class, () -> {
            money.setAmount(amount);
        });
    }

    @Test 
    public void testMoneyAdd() {
        Money money1 = new Money(200);
        Money money2 = new Money(300);
        money1.add(money2);
        assertEquals(500, money1.getAmount());
    }

    @Test
    public void testMoneyAddOverflow() {
        Money money1 = new Money(Long.MAX_VALUE);
        Money money2 = new Money(1);
        assertThrows(ArithmeticException.class, () -> {
            money1.add(money2);
        });
    }

    @Test
    public void testMoneySubtract_ValidValue() {
        Money money1 = new Money(500);
        Money money2 = new Money(200);
        money1.subtract(money2);
        assertEquals(300, money1.getAmount());
    }

    @Test
    public void testMoneySubtract_InvalidValue() {
        Money money1 = new Money(200);
        Money money2 = new Money(500);
        assertThrows(IllegalArgumentException.class, () -> {
            money1.subtract(money2);
        });
    }

    @Test
    public void testMoneyCompareTo() {
        Money money1 = new Money(300);
        Money money2 = new Money(200);
        Money money3 = new Money(300);
        assertAll("compareTo tests",
            () -> assertEquals(1, money1.compareTo(money2)),
            () -> assertEquals(-1, money2.compareTo(money1)),
            () -> assertEquals(0, money1.compareTo(money3))
        );
    }

}
