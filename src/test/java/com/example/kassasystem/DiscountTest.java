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
        public void testCreatePercentileDiscount(int percentile){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name2", SalesTax.MEDIUM, new Money(1000), 1, barcode);
        Discount discount = new Discount("Discount name", DiscountType.PERCENTILE, percentile, objectToRecieveDiscount1, objectToRecieveDiscount2);
        assertAll(
                "Discount contains both items + values set properly",
                () -> assertEquals(DiscountType.PERCENTILE, discount.getDiscountType()),
                () -> assertEquals(percentile, discount.getValue()),
                () -> assertEquals("Discount name", discount.getName()),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount1)),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount2))
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
    @ValueSource(ints = {100, 250, 1000})
    public void testCreateFixedAmountDiscount(int fixedAmount){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name2", SalesTax.MEDIUM, new Money(1000), 1, barcode);
        Discount discount = new Discount("Discount name", DiscountType.FIXED_AMOUNT, fixedAmount, objectToRecieveDiscount1, objectToRecieveDiscount2);
        assertAll(
                "Discount contains both items + values set properly",
                () -> assertEquals(DiscountType.FIXED_AMOUNT, discount.getDiscountType()),
                () -> assertEquals(fixedAmount, discount.getValue()),
                () -> assertEquals("Discount name", discount.getName()),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount1)),
                () -> assertTrue(discount.getItems().contains(objectToRecieveDiscount2))
        );      
        }

    @ParameterizedTest
    @ValueSource(ints = {0, -10, -100})
    public void testCreateFixedAmountDiscountZeroOrNegativeThrowsException(int fixedAmount){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount = new AmountPriceItem("name", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        assertThrows(IllegalArgumentException.class, () -> {
            new Discount("Discount name", DiscountType.FIXED_AMOUNT, fixedAmount, objectToRecieveDiscount);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1001, 2000})
    public void testCreateFixedAmountDiscountExceedingItemPriceThrowsException(int fixedAmount){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount = new AmountPriceItem("name", SalesTax.LOW, new Money(1000), 0, 1, barcode); 
        assertThrows(IllegalArgumentException.class, () -> {
            new Discount("Discount name", DiscountType.FIXED_AMOUNT, fixedAmount, objectToRecieveDiscount);
        });
    }

    @Test
    public void testCreateDiscountWithoutItems_ThrowsException(){
        assertAll(
            "Creating discount without items throws exception",
            () -> assertThrows(IllegalArgumentException.class, () -> { new Discount("Discount name", DiscountType.PERCENTILE, 10);}),
            () -> assertThrows(IllegalArgumentException.class, () -> { new Discount("Discount name", DiscountType.FIXED_AMOUNT, 100);})
        );      
    }


}
