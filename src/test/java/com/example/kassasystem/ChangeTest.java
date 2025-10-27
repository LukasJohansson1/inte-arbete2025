package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ChangeTest {

    @Test
    public void testExchangeMoney_Valid(){
        Money price = new Money(5000); 
        Money paid = new Money(15000); 

        Money expectedResult = new Money(10000);

        assertEquals(expectedResult.getAmount(), Change.exchangeMoney(price, paid).getAmount());
    }

    @Test
    public void testExchangeMoney_PaidLessThanPrice_ThrowsException(){
        Money price = new Money(100000);
        Money paid = new Money(5000);

        assertThrows(IllegalArgumentException.class, () -> {
            Change.exchangeMoney(price, paid);
        });
    }

    @Test
    public void testExchangeMoney_PaidIsZero_ThrowsException(){
        Money price = new Money(0);
        Money paid = new Money(0);

        assertThrows(IllegalArgumentException.class, () -> {
            Change.exchangeMoney(price, paid);
        });
    }

    @Test 
    public void testExchangeMoney_AmountsAreEqual_ShouldReturnZeroChange(){
        Money price = new Money(1000);
        Money paid = new Money(1000);

        Money expectedResult = new Money(0);

        assertEquals(expectedResult.getAmount(), Change.exchangeMoney(price, paid).getAmount());
    }

}
