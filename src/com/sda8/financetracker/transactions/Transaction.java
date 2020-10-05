package com.sda8.financetracker.transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Transaction {
    LocalDate date;
    double transactionValue;
    String transactionDescription;
    String transactionType;

    public Transaction(LocalDate date, double transactionValue, String transactionType, String transactionDescription) {
        this.date = date;
        this.transactionValue = transactionValue;
        this.transactionDescription = transactionDescription;
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(double transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    @Override
    public String toString() {
        return "\tDate: " + date +
                "\n\tAmount: " + transactionValue +
                "\n\tDescription : " + transactionDescription +
                "\n\tType: " + transactionType;
    }
}