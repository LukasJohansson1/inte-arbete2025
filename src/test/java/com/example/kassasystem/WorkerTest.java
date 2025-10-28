package com.example.kassasystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
// Låter co-pilot hantera alla kommentarer

public class WorkerTest {
    

    @Test
    public void testHandleRate(){
        Worker w = new Worker("Anna", 30); // Name and handle rate in items per hour
        assertEquals("Anna", w.getName());
        assertEquals(30, w.getHandleRate());
        assertEquals(0, w.getTotalSales().getAmount());
    } // Verify that worker properties are set correctly

    @Test
    public void testCalculateBonus(){
        Worker w = new Worker("Johan", 25);
        w.addSales(new Money(1000000)); // 10,000.00 SEK
        w.addSales(new Money(500000));  // 5,000.00 SEK
        Money bonus = w.calculateBonus();

        assertEquals(75000, bonus.getAmount());
        assertEquals("750.00 SEK", bonus.toString());
    } // Verify that bonus calculation is correct

    @Test
    public void testMonthlyReset(){
        Worker w = new Worker("Erik", 20);
        w.addSales(new Money(100000));
        Money bonusBeforeReset = w.calculateBonus();
        assertEquals(5000, bonusBeforeReset.getAmount());

        w.resetMonthlySales();
        Money bonusAfterReset = w.calculateBonus();
        assertEquals(0, bonusAfterReset.getAmount());
        assertEquals(0, w.getSalesRecords().size());
    } // Ensure that monthly reset works correctly

    @Test
    public void testSalesHistory() {
        Worker w = new Worker("Mia", 15);
        Money sale1 = new Money(20000);
        Money sale2 = new Money(30000);
        w.addSales(sale1);
        w.addSales(sale2);

        assertEquals(2, w.getSalesRecords().size());
        assertEquals(sale1.getAmount(), w.getSalesRecords().get(0).getAmount());
        assertEquals(sale2.getAmount(), w.getSalesRecords().get(1).getAmount());
        
    } // Verify that sales history is recorded correctly

    @Test
    public void testCalculateBonusNoSales(){
        Worker w = new Worker("Nina", 10);
        Money bonus = w.calculateBonus();
        assertEquals(0, bonus.getAmount());
    } // Ensure that bonus is zero when there are no sales

    @Test
    public void testStateTransitions(){
        Worker w = new Worker("Emma", 20);
        // Initial state

        assertEquals(0, w.getTotalSales().getAmount());
        Money bonus = w.calculateBonus();
        assertEquals(0, bonus.getAmount());

        // After adding sales
        Money sale1 = new Money(100000);
        w.addSales(sale1);
        assertEquals(100000, w.getTotalSales().getAmount());
        assertEquals(1, w.getSalesRecords().size());

        // After adding more sales
        Money sale2 = new Money(50000);
        w.addSales(sale2);
        assertEquals(150000, w.getTotalSales().getAmount());
        assertEquals(2, w.getSalesRecords().size());

        // Calculate bonus
        Money calculateBonus = w.calculateBonus();
        assertEquals(7500, calculateBonus.getAmount());

        w.resetMonthlySales();
        assertEquals(0, w.getTotalSales().getAmount());
    } // Test various state transitions of the Worker class

    @Test
    public void testGetHighestSale(){
        Worker w = new Worker("Olivia", 18);
        assertEquals("Olivia: 0.00 SEK", w.getHighestSale()); 
        // Verify that highest sale is zero when no sales exist

        w.addSales(new Money(25000));
        w.addSales(new Money(40000));
        w.addSales(new Money(15000));

        assertEquals("Olivia: 400.00 SEK", w.getHighestSale());
    } // Verify that highest sale is returned correctly

    @Test
    public void testGetHighestSaleSingle() {
        Worker w = new Worker("Sara", 10);
        w.addSales(new Money(10000));
        assertEquals("Sara: 100.00 SEK", w.getHighestSale());
    } // Verify highest sale with single entry

    @Test
    public void testCalculateEfficiency(){
        Worker w = new Worker("Kevin", 20);
        w.addSales(new Money(100000));
        double efficieny = w.calculateEfficiency();
        assertEquals(5000.0, efficieny);
    } // Verify that efficiency is calculated correctly

    @Test
    public void testCalculateEfficiencyZeroHandleRate(){
        Worker w = new Worker("Tom", 0);
        w.addSales(new Money(50000));
        double efficiency = w.calculateEfficiency();
        assertEquals(0.0, efficiency);
    } // Ensure that efficiency is zero when handle rate is zero

    @Test
    public void testItemsHandledPerHour(){
        Worker w = new Worker("Liam", 15);
        assertEquals(15, w.getHandleRate());
    } // Verify that items handled per hour is returned correctly

    @Test
    public void testItemsHandledPerHourCalculation(){
        Worker w = new Worker("Liam", 15);
        
        assertEquals(0, w.itemsHandledPerHour(0));
        assertEquals(15, w.itemsHandledPerHour(1));
        assertEquals(75, w.itemsHandledPerHour(5));
    } // Verify that items handled per hour calculation is correct
}
