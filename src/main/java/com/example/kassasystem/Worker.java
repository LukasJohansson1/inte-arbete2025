package com.example.kassasystem;

import java.util.ArrayList;
import java.util.List;

// Låter co-pilot hantera alla kommentarer

public class Worker {
    private String name;
    private int handleRate; 
    private Money totalSales;
    private double bonusRate = 0.05;
    private List<Money> salesRecords = new ArrayList<>();

    public Worker(String name, int handleRate){
        this.name = name;
        this.handleRate = handleRate;
        this.totalSales = new Money(0);
        this.salesRecords = new ArrayList<>();
        this.bonusRate = 0.05;
    }

    public String getName(){
        return name;
    }

    public int getHandleRate(){
        return handleRate;
    }

    public Money getTotalSales(){
        return totalSales;
    }

    public void addSales(Money saleAmount){
        if (saleAmount.getAmount() < 0) {
            throw new IllegalStateException("Amount must be positive");
        }
        totalSales.add(saleAmount);
        salesRecords.add(saleAmount);

    }

    public Money calculateBonus(){
        long bonusAmount = (long) (totalSales.getAmount() * bonusRate);
        return new Money(bonusAmount);
    }

    public void resetMonthlySales() {
        totalSales = new Money(0);
        salesRecords.clear();
    }

    public List<Money> getSalesRecords() {
        return new ArrayList<>(salesRecords);
    }

    public String getHighestSale(){
        if(salesRecords.isEmpty()){
            return name + ": 0.00 SEK";
        }
        Money highestSale = salesRecords.get(0);
        for(Money sale : salesRecords){
            if(sale.getAmount() > highestSale.getAmount()){
                highestSale = sale;
            }
        }
        return name + ": " + highestSale.toString();
    }

    public double calculateEfficiency(){
        if(handleRate == 0){
            return 0.0;
        }
        return (double) totalSales.getAmount() / handleRate;
    }

    public int itemsHandledPerHour(int hoursWorked){
        return handleRate * hoursWorked;
    }
    
}