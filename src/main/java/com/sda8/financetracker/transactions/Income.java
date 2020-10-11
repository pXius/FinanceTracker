package com.sda8.financetracker.transactions;

import java.io.Serializable;
import java.time.LocalDate;

public class Income extends Transaction implements Serializable {
    public Income(LocalDate date, double transactionValue, String transactionType, String transactionDescription) {
        super(date, transactionValue, transactionType, transactionDescription);
    }

    @Override
    public String toString() {
        return "Income:\n" + super.toString();
    }
}
