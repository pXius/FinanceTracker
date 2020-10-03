package com.sda8.financetracker.transactions;

public class Income extends Transaction{
    public Income(int year, int month, int day, double transactionValue, String transactionType, String transactionDescription) {
        super(year, month, day, transactionValue, transactionType, transactionDescription);
    }

    @Override
    public String toString() {
        return "Income:\n" + super.toString();
    }
}
