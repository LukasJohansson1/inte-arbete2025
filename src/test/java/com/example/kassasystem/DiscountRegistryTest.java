package com.example.kassasystem;


import org.junit.jupiter.api.*;

import static org.junit.Assert.*;
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
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name2", SalesTax.HIGH, new Money(500), 1000, barcode);

        Discount discount = new Discount("test", DiscountType.PERCENTILE, 10, objectToRecieveDiscount1, objectToRecieveDiscount2);
        discountRegistry.addDiscount(discount);

        assertTrue(discountRegistry.getAllDiscounts().containsKey(discount));

    }

    @Test 
    public void testGetDiscountsForItem(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode);
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 1, barcode);

        Discount discount = new Discount("test", DiscountType.PERCENTILE, 10, objectToRecieveDiscount1);
        discountRegistry.addDiscount(discount);


        assertAll("Discount retrieved for correct item",
                () -> assertEquals(1, discountRegistry.getDiscountsForItem(objectToRecieveDiscount1).size()),
                () -> assertEquals(0, discountRegistry.getDiscountsForItem(objectToRecieveDiscount2).size())
        );

    }

    @Test
    public void testCalculateDiscountedPriceFixedAmount(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode);
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 1, barcode);

        Discount discount = new Discount("test", DiscountType.FIXED_AMOUNT, 100, objectToRecieveDiscount1, objectToRecieveDiscount2);
        discountRegistry.addDiscount(discount);


        assertAll("Fixed amount discount applied correctly",
                () -> assertEquals(900, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount1).getAmount()),
                () -> assertEquals(900, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount2).getAmount())
        );
    }

    @Test
    public void testCalculateDiscountedPricePercentile(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        WeightPriceItem objectToRecieveDiscount1 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 100, barcode);
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 100, barcode);

        Discount discount = new Discount("test", DiscountType.PERCENTILE, 10, objectToRecieveDiscount1, objectToRecieveDiscount2);
        discountRegistry.addDiscount(discount);

        assertAll("Percentile discount applied correctly",
                () -> assertEquals(90000, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount1).getAmount()),
                () -> assertEquals(90000, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount2).getAmount())
        );

    }

    @Test
    public void testCalculateDiscountedPriceNoDiscountShouldReturnOriginalPrice(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode);
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 1, barcode);

        assertAll(
                "No discounts applied, original price returned",
                () -> assertEquals(1000, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount1).getAmount()),
                () -> assertEquals(1000, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount2).getAmount())
        );

    }

    @Test
    public void testCalculateDiscountedPriceAppliesFixedAmountBeforePercentile(){
        EANBarcode barcode = new EANBarcode("4006381333931");
        AmountPriceItem objectToRecieveDiscount1 = new AmountPriceItem("name1", SalesTax.LOW, new Money(1000), 0, 1, barcode);
        WeightPriceItem objectToRecieveDiscount2 = new WeightPriceItem("name1", SalesTax.LOW, new Money(1000), 1, barcode);

        Discount percentileDiscount = new Discount("percentile", DiscountType.PERCENTILE, 10, objectToRecieveDiscount1, objectToRecieveDiscount2);
        Discount fixedAmountDiscount = new Discount("fixed", DiscountType.FIXED_AMOUNT, 100, objectToRecieveDiscount1, objectToRecieveDiscount2);
        discountRegistry.addDiscount(fixedAmountDiscount);
        discountRegistry.addDiscount(percentileDiscount);

        assertAll("Fixed amount discount applied before percentile discount",
                () -> assertEquals(810, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount1).getAmount()),
                () -> assertEquals(810, discountRegistry.calculateDiscountedPrize(objectToRecieveDiscount2).getAmount())
        );


    }

}
