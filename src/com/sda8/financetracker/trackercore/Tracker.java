package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;

import java.util.ArrayList;

public class Tracker {
    private double balance;
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;

    public Tracker() {
        this.balance = 0;
        expenseList = new ArrayList<>();
        incomeList = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void printBalance() {
        System.out.println("Your Balance is: " + this.balance + "\n");
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public void addIncome(Income income) {
        incomeList.add(income);
    }
}
