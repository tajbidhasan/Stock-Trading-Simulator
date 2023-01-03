package com.example.homework10;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String username;
    private String password;
    private double cash;
    private UserWallet wallet;
     private  ArrayList<String> history;




    public User(String name,String username,String password) {
        this.name= name;
        this.username = username;
        this.password = password;
        this.cash=10000;
        this.wallet= new UserWallet();
        this.history = new ArrayList<String>();


    }
    public User(String username,String password) {
        this.cash=10000;
        this.username = username;
        this.password = password;
        this.wallet= new UserWallet();
        this.history = new ArrayList<>();

    }

    public String getHistory() {
        return "*"+ history;
    }

    public void setHistory(String history) {
        System.out.println(history);
        System.out.println(this.history.size());
        this.history.add(" " + history);
    }

    public UserWallet getWallet() {
        return wallet;
    }

    public void setWallet(UserWallet wallet) {
        this.wallet = wallet;
    }

    public double getCash() {
        return cash;
    }

    public String getPassword() {
        return password;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof User))
            return false;

        User user = (User)obj;
        return (user.password.equals(this.password) &&
                user.username.equals(this.username));
    }
}
