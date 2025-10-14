package com.example.kassasystem;


import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        Money money = new Money(5005);   
        String expected = "50.05 SEK";
        assertEquals(expected, money.toString());

    }

    @Test
    public void testMoneySetAmountPositive() {
        Money money = new Money(1000);
        money.setAmount(250);
        assertEquals(250, money.getAmount());

    }

    @Test
    public void testMoneySetAmountNegative() {
        Money money = new Money(100);
        assertThrows(IllegalArgumentException.class, () -> {
            money.setAmount(-100);
        });
    }

    @Test
    public void testMoneySetAmountZero() {
        Money money = new Money(100);
        money.setAmount(0);
        assertEquals(0, money.getAmount());
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
    public void testMoneySubtract() {
        Money money1 = new Money(500);
        Money money2 = new Money(200);
        money1.subtract(money2);
        assertEquals(300, money1.getAmount());
    }

}
