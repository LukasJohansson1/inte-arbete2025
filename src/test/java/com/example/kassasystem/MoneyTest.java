package com.example.kassasystem;

import org.junit.*;
import static org.junit.Assert.*;


public class MoneyTest {

    @Test
    public void testMoneyCreation() {
        Money money = new Money(100);
        assertEquals(100, money.getAmount());
        
}
    @Test
    public void testMoneyCreationNegative() {
        assertThrows(IllegalStateException.class, () -> {
        Money m = new Money(-50);
        });
    }

    @Test
    public void testMoneyCreationZero() {
        assertThrows(IllegalStateException.class, () -> {
        Money money = new Money(0);
        });
    }

    @Test
    public void testMoneyToString() {
        Money money = new Money(500);   
        String expected = "500 SEK";
        assertEquals(expected, money.toString());

    }

    @Test
    public void testMoneySetAmount() {
        Money money = new Money(0);
        money.setAmount(250);
        assertEquals(250, money.getAmount());

    }

    @Test
}
