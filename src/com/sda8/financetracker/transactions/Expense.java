package com.sda8.financetracker.transactions;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense extends Transaction implements Serializable {
    public Expense(LocalDate date, double transactionValue, String transactionType, String transactionDescription) {
        super(date, transactionValue, transactionType, transactionDescription);
    }

    @Override
    public String toString() {
        return "Expense:\n" + super.toString();
    }
}
