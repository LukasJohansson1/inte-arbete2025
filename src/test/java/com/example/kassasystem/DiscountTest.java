package com.example.kassasystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DiscountTest {

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100})
    public void testCreatePercentileDiscountForOneItem(int percentile){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount = new AmountPriceItem("name", SalesTax.LOW, new Money(1000), 0, 1, barcode);

        Discount discount = new Discount("Discount name", DiscountType.PERCENTILE, percentile, objectToRecieveDiscount);
        assertAll(
                "Discount properties",
                () -> assertEquals("Discount name", discount.getName()),
                () -> assertEquals(DiscountType.PERCENTILE, discount.getDiscountType()),
                () -> assertEquals(percentile, discount.getValue()),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount))
        );
        
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    public void testCreatePercentileDiscountNegativeThrowsException(int percentile){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount = new AmountPriceItem("name", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        assertThrows(IllegalArgumentException.class, () -> {
            new Discount("Discount name", DiscountType.PERCENTILE, percentile, objectToRecieveDiscount);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 150, 200})
    public void testCreatePercentileDiscountOver100ThrowsException(int percentile){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount = new AmountPriceItem("name", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        assertThrows(IllegalArgumentException.class, () -> {
            new Discount("Discount name", DiscountType.PERCENTILE, percentile, objectToRecieveDiscount);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100})
    public void testCreatePercentileDiscountForSeveralItems(int percentile){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        AmountPriceItem objectToRecieveDiscount2 = new AmountPriceItem("name2", SalesTax.MEDIUM, new Money(1000), 0, 1, barcode); 
        Discount discount = new Discount("Discount name", DiscountType.PERCENTILE, percentile, objectToRecieveDiscount1, objectToRecieveDiscount2);
        assertAll(
                "Discount contains both items",
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount1)),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount2))
        );
        
    }

    @Test
    public void testCreateDiscountWithoutItems_ThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Discount("Discount name", DiscountType.PERCENTILE, 10);
        });
    }

}
