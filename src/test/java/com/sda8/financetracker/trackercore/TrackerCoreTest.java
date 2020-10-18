package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackerCoreTest {

    @Test
    void balanceAdjustment() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        trackerCore.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        trackerCore.adjustBalance(499);
        double result = trackerCore.getBalance();
        assertEquals(10000, result);
    }

    @Test
    void addExpense() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        String result = trackerCore.getExpenseList().get(0).getTransactionDescription();
        assertEquals("Expense No. 1", result);
    }

    @Test
    void addIncome() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        String result = trackerCore.getIncomeList().get(0).getTransactionDescription();
        assertEquals("Income No. 1", result);
    }

    @Test
    void deleteTransaction() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        trackerCore.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        trackerCore.deleteTransaction(trackerCore.getExpenseList().get(0));
        assertEquals(trackerCore.getExpenseList().size(), 0);
    }

    @Test
    void sortByDate() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 4"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 5"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 6"));
        List<Transaction> result = trackerCore.sortByDate(trackerCore.getIncomeList(), true);
        assertEquals("13 October 2020", result.get(0).getDateString());
        assertEquals("12 October 2020", result.get(1).getDateString());
        assertEquals("11 October 2020", result.get(2).getDateString());
    }

    @Test
    void sortByTransactionValue() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 256, "Type 1", "Income No. 2"));
        trackerCore.addIncome(new Income(LocalDate.now(), 512, "Type 1", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now(), 128, "Type 1", "Income No. 1"));
        List<Transaction> result = trackerCore.sortByTransactionValue(trackerCore.getIncomeList(), false);
        assertEquals(128, result.get(0).getTransactionValue());
        assertEquals(256, result.get(1).getTransactionValue());
        assertEquals(512, result.get(2).getTransactionValue());
    }

    @Test
    void sortByTransactionValueReversed() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 256, "Type 1", "Income No. 2"));
        trackerCore.addIncome(new Income(LocalDate.now(), 512, "Type 1", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now(), 128, "Type 1", "Income No. 1"));
        List<Transaction> result = trackerCore.sortByTransactionValue(trackerCore.getIncomeList(), true);
        assertEquals(512, result.get(0).getTransactionValue());
        assertEquals(256, result.get(1).getTransactionValue());
        assertEquals(128, result.get(2).getTransactionValue());
    }

    @Test
    void sortByDescription() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 699, "Type 1", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now(), 499, "Type 1", "Income No. 1"));
        trackerCore.addIncome(new Income(LocalDate.now(), 599, "Type 1", "Income No. 2"));
        List<Transaction> result = trackerCore.sortByDescription(trackerCore.getIncomeList());
        assertEquals("Income No. 1", result.get(0).getTransactionDescription());
        assertEquals("Income No. 2", result.get(1).getTransactionDescription());
        assertEquals("Income No. 3", result.get(2).getTransactionDescription());
    }

    @Test
    void sortByType() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 100, "Type 3", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now(), 200, "Type 1", "Income No. 1"));
        trackerCore.addIncome(new Income(LocalDate.now(), 300, "Type 2", "Income No. 2"));
        List<Transaction> result = trackerCore.sortByType(trackerCore.getIncomeList());
        assertEquals("Type 1", result.get(0).getTransactionType());
        assertEquals("Type 2", result.get(1).getTransactionType());
        assertEquals("Type 3", result.get(2).getTransactionType());
    }

    @Test
    void mergeTransactionList() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        trackerCore.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        List<Transaction> result = trackerCore.mergeTransactionList(trackerCore.getExpenseList(), trackerCore.getIncomeList());
        assertEquals(499, result.get(0).getTransactionValue());
        assertEquals(10000, result.get(1).getTransactionValue());
    }

    @Test
    void filterListByDate() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 1"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 2"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(4), 4000, "Type 1", "Income No. 4"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(3), 3000, "Type 1", "Income No. 5"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(2), 2000, "Type 1", "Income No. 6"));
        List<Transaction> result = trackerCore.filterListByDate(trackerCore.getIncomeList(), LocalDate.now().minusDays(5), LocalDate.now().minusDays(2));
        assertEquals("Income No. 3", result.get(0).getTransactionDescription());
        assertEquals("Income No. 4", result.get(1).getTransactionDescription());
        assertEquals("Income No. 5", result.get(2).getTransactionDescription());
        
    }

    @Test
    void filterByKeyword() {
        TrackerCore trackerCore = new TrackerCore();
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 1"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 2"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 3"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(4), 4000, "Type 1", "Income No. 4"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(3), 3000, "Type 1", "Income No. 5"));
        trackerCore.addIncome(new Income(LocalDate.now().minusDays(2), 2000, "Type 1", "Income No. 6"));
        List<Transaction> result = trackerCore.filterByKeyword(trackerCore.getIncomeList(),"3");
        assertEquals("Income No. 3", result.get(0).getTransactionDescription());
    }


}