package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.UiText;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tracker implements Serializable {
    private double balance;
    private List<Expense> expenseList;
    private List<Income> incomeList;

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

    public List<Expense> getExpenseList() {
        return this.expenseList;
    }

    public List<Income> getIncomeList() {
        return this.incomeList;
    }



    public <T extends Transaction> List<T> sortByDate(List<T> list, boolean reversed) {
        if (reversed){
            return list.stream()
                    .sorted(Comparator.comparing(T::getDate)
                            .reversed())
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .sorted(Comparator.comparing(T::getDate))
                    .collect(Collectors.toList());
        }
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

    public <T extends Transaction> List<Transaction> mergeTransactionList(List<Expense> expenseList, List<Income> incomeList) {
        return Stream.concat(
                expenseList.stream(),
                incomeList.stream())
                .collect(Collectors.toList());
    }


    //Readability T
    public <T extends Transaction> List<T> filterListByDate(List<T> listToFilter, LocalDate startDate, LocalDate endDate){
        return  listToFilter.stream()
                .filter(T -> T.getDate().compareTo(startDate) >= 0 && T.getDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }
}
