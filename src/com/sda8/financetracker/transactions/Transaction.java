package com.sda8.financetracker.transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {
    LocalDate date;
    double transactionValue;
    String transActionDescription;

    public Transaction(int year, int month, int day, double transactionValue, String transActionDescription) {
        this.date = LocalDate.of(year, month, day);
        this.transactionValue = transactionValue;
        this.transActionDescription = transActionDescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }


    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    public double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(double transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getTransActionDescription() {
        return transActionDescription;
    }

    public void setTransActionDescription(String transActionDescription) {
        this.transActionDescription = transActionDescription;
    }
}
