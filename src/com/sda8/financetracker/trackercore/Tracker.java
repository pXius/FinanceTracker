package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.UiText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tracker implements Serializable {
    private double balance;
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;

    public Tracker() {
        this.balance = 0;
        this.expenseList = new ArrayList<>();
        this.incomeList = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void printBalance() {
        UiText.balanceText(this.balance);
    }

    public void addExpense(Expense expense) {
        this.balance -= expense.getTransactionValue();
        expenseList.add(expense);
    }

    public void addIncome(Income income) {
        this.balance += income.getTransactionValue();
        incomeList.add(income);
    }

    public ArrayList<Expense> getExpenseList() {
        return this.expenseList;
    }

    public ArrayList<Income> getIncomeList() {
        return this.incomeList;
    }

    public <T extends Transaction> List<T> sortByDate(List<T> list) {
        return list.stream()
                .sorted(Comparator.comparing(T::getDate)
                        .reversed())
                .collect(Collectors.toList());
    }

    public <T extends Transaction> List<T> sortByTransactionValue(List<T> list, boolean reversed) {
        if (!reversed) {
            return list.stream()
                    .sorted(Comparator.comparing(T::getTransactionValue))
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .sorted(Comparator.comparing(T::getTransactionValue)
                            .reversed())
                    .collect(Collectors.toList());
        }
    }

    public <T extends Transaction> List<T> mergeTransactionList(List<T> list1, List<T> list2) {
        return Stream.concat(
                list1.stream(),
                list2.stream())
                .collect(Collectors.toList());
    }
}
