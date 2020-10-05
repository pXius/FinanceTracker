package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public ArrayList<Expense> getExpenseList() {
        return expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return incomeList;
    }

    public <T extends Transaction> List<T> sortByDate(List<T> list) {
        return  list.stream()
                .sorted(Comparator.comparing(T::getDate)
                .reversed())
                .collect(Collectors.toList());
    }
}
