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

/**
 * The Tracker class is designed to store Transaction objects. The class also provides methods necessary to
 * transform the Transaction data by means of sorting and filtering the list data structures in which
 * they are stored. The class is serializable and can be stored as a data object to restore program state at
 * a later stage.
 */
public class Tracker implements Serializable {
    private static final long serialVersionUID = 5912330287419915858L;
    private double balance;
    private final List<Expense> expenseList;
    private final List<Income> incomeList;

    /**
     * Calling the constructor creates the list data structures to store the Transaction objects, as well
     * as setting the balance to 0.
     */
    public Tracker() {
        this.balance = 0;
        this.expenseList = new ArrayList<>();
        this.incomeList = new ArrayList<>();
    }

    /**
     * Returns the balance field value of type double.
     * @return the balance field value of type double.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Adjusts the balance field value with the passed argument value. Negative values are thus
     * required to lower the balance field value.
     * @param adjustment this value will be added to the balance field value.
     */
    public void adjustBalance(double adjustment) {
        this.balance += adjustment;
    }

    /**
     * Prints the current balance field value to the console.
     */
    public void printBalance() {
        UiText.balanceText(this.balance);
    }

    /**
     * Adds an expense object to the expenseList datastructures.
     * @param expense This parameter requires an Object of type Expense, which extends the Transaction class.
     */
    public void addExpense(Expense expense) {
        this.balance -= expense.getTransactionValue();
        expenseList.add(expense);
    }

    /**
     * Adds an income object to the expenseList datastructures.
     * @param income This parameter requires an Object of type Income, which extends the Transaction class.
     */
    public void addIncome(Income income) {
        this.balance += income.getTransactionValue();
        incomeList.add(income);
    }


    /**
     * A method to delete transactions form the respective transaction lists.
     * Detects what type of transaction is passed and removes it.
     * @param transaction the transaction to be removed.
     * @return boolean verification on successful removal.
     */
    public boolean deleteTransaction(Transaction transaction) {
        List<? extends Transaction> transactionList = transaction instanceof Expense ? expenseList : incomeList;
        return transactionList.remove(transaction);
    }

    /**
     * Returns a list of Expense objects stored in the Tracker object.
     * @return a list of Expense objects stored in the Tracker object.
     */
    public List<Expense> getExpenseList() {
        return this.expenseList;
    }

    /**
     * Returns a list of Income objects stored in the Tracker object.
     * @return a list of Income objects stored in the Tracker object.
     */
    public List<Income> getIncomeList() {
        return this.incomeList;
    }

    /**
     * Returns a list of Transaction objects sorted by date. Dy default the newest date will be the
     * first item in the list. By setting reversed to true, the list will be reversed.
     * @param list A list of Transaction objects to be sorted.
     * @param reversed setting to true will reverse the order of the list returned. Placing the oldest date first in the list.
     * @return a list of date sorted transaction objects.
     */
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

    /**
     * Returns a list of Transaction objects sorted by transaction value. By default, the lowest transaction
     * value object will be the first in the list. The list order can be reversed by setting the
     * boolean "reversed" argument to true.
     * @param list List of Transaction objects to be sorted.
     * @param reversed setting to true will reverse the order of the list returned. Placing the lowest value Transaction first in the list.
     * @return a list of Transaction value sorted Objects.
     */
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

    /**
     * This method takes two lists of type Transaction or a type that extends transaction and combines the into a single list.
     * The lists are combined in a append fashion and the returned list will be unsorted.
     * @param list1 a Transaction list to combine with another list.
     * @param list2 a Transaction list to combine with another list.
     * @return a single Transaction list by combining the passed transaction lists.
     */
    public List<Transaction> mergeTransactionList(List<? extends Transaction> list1, List<? extends Transaction> list2) {
        return Stream.concat(
                expenseList.stream(),
                incomeList.stream())
                .collect(Collectors.toList());
    }

    /**
     * Takes a list and two date parameters to filter Transactions for a specific period.
     * Returns a list of transactions filtered by date.
     * @param listToFilter the list of Transactions to be filtered to a shorter period.
     * @param startDate the starting period of the filtered list.
     * @param endDate ending period of the filtered list.
     * @return a list of transactions between the two passed dates.
     */
    public List<Transaction> filterListByDate(List<? extends Transaction> listToFilter, LocalDate startDate, LocalDate endDate) {
        return listToFilter.stream()
                .filter(transaction -> transaction.getDate().compareTo(startDate) >= 0 && transaction.getDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    /**
     *  Takes a list of Transaction objects and filters them into a new list containing only Transaction
     *  objects that have they keyphrase in either their description or type string fields.
     * @param listToFilter the list to filter.
     * @param searchPhrase String phrase to search for.
     * @return a list of Transaction objects with the search phrase in either type or description.
     */
    public List<Transaction> filterByKeyword(List<? extends Transaction> listToFilter, String searchPhrase) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction transaction : listToFilter){
            if ((transaction.getTransactionDescription().toLowerCase().contains(searchPhrase)) || (transaction.getTransactionType().toLowerCase().contains(searchPhrase)))
                filteredList.add(transaction);
        }
        return filteredList;
    }

    /**
     * Alphabetically sorts a passed list of Transactions by the their description fields.
     * @param listToSort the list of Transaction objects to be sorted.
     * @return a list of Transactions alphabetically sorted by their description string fields.
     */
    public List<Transaction> sortByDescription(List<? extends Transaction> listToSort) {
        return listToSort
                .stream()
                .sorted(Comparator.comparing(transaction -> transaction.getTransactionDescription().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Alphabetically sorts a passed list of Transactions by the their type fields.
     * @param listToSort the list of Transaction objects to be sorted.
     * @return a list of Transactions alphabetically sorted by their type string fields.
     */
    public List<Transaction> sortByType(List<? extends Transaction> listToSort) {
        return listToSort
                .stream()
                .sorted(Comparator.comparing(transaction -> transaction.getTransactionType().toLowerCase()))
                .collect(Collectors.toList());
    }

}
