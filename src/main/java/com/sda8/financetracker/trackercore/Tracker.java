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
    private static final long serialVersionUID = 5912330287419915858L;
    private double balance;
    private final List<Expense> expenseList;
    private final List<Income> incomeList;

    public Tracker() {
        this.balance = 0;
        this.expenseList = new ArrayList<>();
        this.incomeList = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void adjustBalance(double adjustment) {
        this.balance += adjustment;
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

    public boolean deleteTransaction(Transaction transaction) {
        List<? extends Transaction> transactionList = transaction instanceof Expense ? expenseList : incomeList;
        return transactionList.remove(transaction);
    }

    public List<Expense> getExpenseList() {
        return this.expenseList;
    }

    public List<Income> getIncomeList() {
        return this.incomeList;
    }

    public List<Transaction> sortByDate(List<? extends Transaction> list, boolean reversed) {
        if (reversed) {
            return list.stream()
                    .sorted(Comparator.comparing(Transaction::getDate)
                            .reversed())
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .sorted(Comparator.comparing(Transaction::getDate))
                    .collect(Collectors.toList());
        }
    }

    public List<Transaction> sortByTransactionValue(List<? extends Transaction> list, boolean reversed) {
        if (!reversed) {
            return list.stream()
                    .sorted(Comparator.comparing(Transaction::getTransactionValue))
                    .collect(Collectors.toList());
        } else {
            return list.stream()
                    .sorted(Comparator.comparing(Transaction::getTransactionValue)
                            .reversed())
                    .collect(Collectors.toList());
        }
    }

    public List<Transaction> mergeTransactionList(List<? extends Transaction> expenseList, List<? extends Transaction> incomeList) {
        return Stream.concat(
                expenseList.stream(),
                incomeList.stream())
                .collect(Collectors.toList());
    }

    public List<Transaction> filterListByDate(List<? extends Transaction> listToFilter, LocalDate startDate, LocalDate endDate) {
        return listToFilter.stream()
                .filter(transaction -> transaction.getDate().compareTo(startDate) >= 0 && transaction.getDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    public List<Transaction> filterByKeyword(List<? extends Transaction> listToFilter, String searchPhrase) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction transaction : listToFilter){
            if ((transaction.getTransactionDescription().toLowerCase().contains(searchPhrase)) || (transaction.getTransactionType().toLowerCase().contains(searchPhrase)))
                filteredList.add(transaction);
        }
        return filteredList;
    }


    //Do lambdas!!!
    public List<Transaction> sortByDescription(List<? extends Transaction> listToSort) {
        return listToSort
                .stream()
                .sorted(Comparator.comparing(transaction -> transaction.getTransactionDescription().toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Transaction> sortByType(List<? extends Transaction> listToSort) {
        return listToSort
                .stream()
                .sorted(Comparator.comparing(transaction -> transaction.getTransactionType().toLowerCase()))
                .collect(Collectors.toList());
    }

}
