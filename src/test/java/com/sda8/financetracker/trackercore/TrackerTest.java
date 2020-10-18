package com.sda8.financetracker.trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackerTest {

    @Test
    void balanceAdjustment() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        tracker.adjustBalance(499);
        double result = tracker.getBalance();
        assertEquals(10000, result);
    }

    @Test
    void addExpense() {
        Tracker tracker = new Tracker();
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        String result = tracker.getExpenseList().get(0).getTransactionDescription();
        assertEquals("Expense No. 1", result);
    }

    @Test
    void addIncome() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        String result = tracker.getIncomeList().get(0).getTransactionDescription();
        assertEquals("Income No. 1", result);
    }

    @Test
    void deleteTransaction() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        tracker.deleteTransaction(tracker.getExpenseList().get(0));
        assertEquals(tracker.getExpenseList().size(), 0);
    }

    @Test
    void sortByDate() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 4"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 5"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 6"));
        List<Transaction> result = tracker.sortByDate(tracker.getIncomeList(), true);
        assertEquals("13 October 2020", result.get(0).getDateString());
        assertEquals("12 October 2020", result.get(1).getDateString());
        assertEquals("11 October 2020", result.get(2).getDateString());
    }

    @Test
    void sortByTransactionValue() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 256, "Type 1", "Income No. 2"));
        tracker.addIncome(new Income(LocalDate.now(), 512, "Type 1", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now(), 128, "Type 1", "Income No. 1"));
        List<Transaction> result = tracker.sortByTransactionValue(tracker.getIncomeList(), false);
        assertEquals(128, result.get(0).getTransactionValue());
        assertEquals(256, result.get(1).getTransactionValue());
        assertEquals(512, result.get(2).getTransactionValue());
    }

    @Test
    void sortByTransactionValueReversed() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 256, "Type 1", "Income No. 2"));
        tracker.addIncome(new Income(LocalDate.now(), 512, "Type 1", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now(), 128, "Type 1", "Income No. 1"));
        List<Transaction> result = tracker.sortByTransactionValue(tracker.getIncomeList(), true);
        assertEquals(512, result.get(0).getTransactionValue());
        assertEquals(256, result.get(1).getTransactionValue());
        assertEquals(128, result.get(2).getTransactionValue());
    }

    @Test
    void sortByDescription() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 699, "Type 1", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now(), 499, "Type 1", "Income No. 1"));
        tracker.addIncome(new Income(LocalDate.now(), 599, "Type 1", "Income No. 2"));
        List<Transaction> result = tracker.sortByDescription(tracker.getIncomeList());
        assertEquals("Income No. 1", result.get(0).getTransactionDescription());
        assertEquals("Income No. 2", result.get(1).getTransactionDescription());
        assertEquals("Income No. 3", result.get(2).getTransactionDescription());
    }

    @Test
    void sortByType() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 100, "Type 3", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now(), 200, "Type 1", "Income No. 1"));
        tracker.addIncome(new Income(LocalDate.now(), 300, "Type 2", "Income No. 2"));
        List<Transaction> result = tracker.sortByType(tracker.getIncomeList());
        assertEquals("Type 1", result.get(0).getTransactionType());
        assertEquals("Type 2", result.get(1).getTransactionType());
        assertEquals("Type 3", result.get(2).getTransactionType());
    }

    @Test
    void mergeTransactionList() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        List<Transaction> result = tracker.mergeTransactionList(tracker.getExpenseList(), tracker.getIncomeList());
        assertEquals(499, result.get(0).getTransactionValue());
        assertEquals(10000, result.get(1).getTransactionValue());
    }

    @Test
    void filterListByDate() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 1"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 2"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(4), 4000, "Type 1", "Income No. 4"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(3), 3000, "Type 1", "Income No. 5"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(2), 2000, "Type 1", "Income No. 6"));
        List<Transaction> result = tracker.filterListByDate(tracker.getIncomeList(), LocalDate.now().minusDays(5), LocalDate.now().minusDays(2));
        assertEquals("Income No. 3", result.get(0).getTransactionDescription());
        assertEquals("Income No. 4", result.get(1).getTransactionDescription());
        assertEquals("Income No. 5", result.get(2).getTransactionDescription());
        
    }

    @Test
    void filterByKeyword() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now().minusDays(7), 7000, "Type 1", "Income No. 1"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(6), 6000, "Type 1", "Income No. 2"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(5), 5000, "Type 1", "Income No. 3"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(4), 4000, "Type 1", "Income No. 4"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(3), 3000, "Type 1", "Income No. 5"));
        tracker.addIncome(new Income(LocalDate.now().minusDays(2), 2000, "Type 1", "Income No. 6"));
        List<Transaction> result = tracker.filterByKeyword(tracker.getIncomeList(),"3");
        assertEquals("Income No. 3", result.get(0).getTransactionDescription());
    }


}