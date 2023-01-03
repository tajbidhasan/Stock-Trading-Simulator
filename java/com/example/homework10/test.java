package com.example.homework10;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        // TODO Auto-generated method stub


        DoubleProperty d1 = new SimpleDoubleProperty();
        DoubleProperty d2 = new SimpleDoubleProperty();

        d1.setValue(30);
        d2.setValue(40);

        System.out.println("d1=" + d1.getValue() + " d2=" + d2.getValue());
        d1.bind(d2);
        System.out.println("d1=" + d1.getValue() + " d2=" + d2.getValue());
        d2.setValue(50);
        System.out.println("d1=" + d1.getValue() + " d2=" + d2.getValue());

        d1.unbind();
        d1.bindBidirectional(d2);
        d1.setValue(60);
        System.out.println("d1=" + d1.getValue() + " d2=" + d2.getValue());
        d2.setValue(70);
        System.out.println("d1=" + d1.getValue() + " d2=" + d2.getValue());

        System.out.println(LocalDate.now());

        LocalDate period1 = LocalDate.now();
        LocalDate period2 = LocalDate.now();
        int year1 = period1.getYear();
        int month1 = period1.getMonthValue();
        int day1 = period1.getDayOfMonth();
        int year2 = period2.getYear();
        int month2 = period2.getMonthValue();
        int day2 = period2.getDayOfMonth();

        long startPeriod = getPeriod(year1, month1, day1);
        long endPeriod = getPeriod(year2, month2, day2);



        LocalDate date = LocalDate.of(2022, 12, 16);
       // period1=1639782387
        // period2=1671318387
        System.out.println(LocalDate.now());
        LocalDate currentDate = LocalDate.now();

// Get the other date
        LocalDate otherDate = LocalDate.of(2022, 12, 16);

// Subtract the other date from the current date
        Period difference = Period.between(otherDate, currentDate);

// Print the difference in years, months, and days
        System.out.println("Years: " + difference.getYears());
        System.out.println("Months: " + difference.getMonths());
        System.out.println("Days: " + difference.getDays());
    }




    public static long getPeriod(int y, int m, int d) {
        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, d);
        Date date = cal.getTime();
        return date.getTime() / 1000;
    }

    //modifying the link
    public String getURL(String stockSymbol, long period1, long period2) {
        String urlTemplate = "https://query1.finance.yahoo.com/v7/finance/download/%s?period1=%d&period2=%d&interval=1d&events=history&includeAdjustedClose=true";
        String urlStr = String.format(urlTemplate, stockSymbol, period1, period2);
        return urlStr;
    }
}
