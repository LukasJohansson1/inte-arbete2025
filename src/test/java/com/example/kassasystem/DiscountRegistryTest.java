package com.example.kassasystem;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DiscountRegistryTest {

    private DiscountRegistry discountRegistry;

    @BeforeEach
    public void setUp(){
        discountRegistry = new DiscountRegistry();
    }

    @Test
    public void testAddDiscount(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode);

        Discount discount = new Discount("test", DiscountType.PERCENTILE, 10, objectToRecieveDiscount1);
        discountRegistry.addDiscount(discount);

        assertTrue(discountRegistry.getAllDiscounts().containsKey(discount));

    }

    @Test 
    public void testGetDiscountsForItem(){

    }

    @Test
    public void testCalculateDiscountedPrice(){

    }

}
