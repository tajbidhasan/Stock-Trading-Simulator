package com.example.homework10;

import java.io.Serializable;
import java.util.ArrayList;

public class UserWallet implements Serializable {
     ArrayList<StockData> stocks;

    public UserWallet(){
        this.stocks= new ArrayList<>();
    }

    public String getStockSym(){
        int i =0;
        while (stocks.size()>0){
            i++;
            return stocks.get(i).getStockName();

        }
       return null;
    }
    public ArrayList<String> getStockNames(){

        ArrayList<String> name = new ArrayList<>();

        for (int i=0; i< stocks.size();i++){
            name.add(stocks.get(i).getStockName());
        }
        return name;
    }

    public void addStock(String stockName, int shares,double price){
        int index = findUserStocks(stockName);
        if(index != -1){
            for(int i  = 0; i<stocks.size(); i++) {
                if (stocks.get(i).getStockName().equals(stockName)) {
                    stocks.get(i).setShareCount(stocks.get(i).getShareCount() + shares);
                    stocks.get(i).setBuyPrice(stocks.get(i).getBuyPrice()+price);
                }
            }
        } else {
            stocks.add(new StockData(stockName, shares,price));
        }
    }

    public int findUserStocks(String stockName){
        for(int i  = 0; i<stocks.size(); i++){
            if(stocks.get(i).getStockName().equals(stockName)){
                return stocks.get(i).getShareCount();
            }
        }
        return -1;
    }
    public double findUserStocksPrice(String stockName){
        for(int i  = 0; i<stocks.size(); i++){
            if(stocks.get(i).getStockName().equals(stockName)){
                return stocks.get(i).getBuyPrice();
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return ""+ stocks ;

    }
}
