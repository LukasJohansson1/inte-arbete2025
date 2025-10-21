package com.example.kassasystem;

import org.junit.jupiter.api.Test;
import java.util.List;

class ReceiptTest {

    @Test
void testGetTotalPriceFas1() {
    AmountPriceItem amountItem = new AmountPriceItem(
        "Mjölk",
        new SalesTax(0.25),
        new Money(1500),
        0,
        2
    );

    WeightPriceItem weightItem = new WeightPriceItem(
        "Banan",
        new SalesTax(0.12),
        new Money(2500),
        1200
    );

    // Test med bara AmountPriceItem
    Receipt receipt1 = new Receipt(List.of(amountItem));
    assert receipt1.getTotalPrice().getAmount() == 1;

    // Test med bara WeightPriceItem
    Receipt receipt2 = new Receipt(List.of(weightItem));
    assert receipt2.getTotalPrice().getAmount() == 1;

    // Test med båda
    Receipt receipt3 = new Receipt(List.of(amountItem, weightItem));
    assert receipt3.getTotalPrice().getAmount() == 2;
}
}