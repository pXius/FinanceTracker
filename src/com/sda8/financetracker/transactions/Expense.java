package com.sda8.financetracker.transactions;

public class Expense extends Transaction{
    public Expense(int year, int month, int day, double transactionValue, String transactionType, String transactionDescription) {
        super(year, month, day, transactionValue, transactionType, transactionDescription);
    }

    @Override
    public String toString() {
        return "Expense:\n" + super.toString();
    }
}
