package com.example.homework10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StockData implements Serializable {
    private String stockName;
    private int shares;
    private double buyPrice;

    public StockData(String stockName, int shares, double price) {
        this.stockName = stockName;
        this.shares = shares;
        this.buyPrice=price;

    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setStockName(String name){
        this.stockName=name;
    }
    public String getStockName(){
        return this.stockName;
    }

    public void setShareCount(int count){
        this.shares=count;
    }
    public int getShareCount(){
        return this.shares;
    }
//https://query1.finance.yahoo.com/v7/finance/download/GM?period1=1639782387&period2=1671318387&interval=1d&events=history&includeAdjustedClose=true
    @Override
    public String toString() {
        return "Stock Symbol:  " + stockName  +
                "   Shares: " + shares + " Value: "+ buyPrice;
    }
}
