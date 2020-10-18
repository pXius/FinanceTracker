package com.sda8.financetracker.transactions;

import com.sda8.financetracker.trackercore.Tracker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getDateString() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        LocalDate result = tracker.getIncomeList().get(0).getDate();
        assertEquals(LocalDate.now(), result);
    }

    @Test
    void getTransactionValue() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        double result = tracker.getExpenseList().get(0).getTransactionValue();
        assertEquals(499,result);
    }

    @Test
    void getTransactionDescription() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        String result = tracker.getExpenseList().get(0).getTransactionDescription();
        assertEquals("Expense No. 1", result);
    }

    @Test
    void getTransactionType() {
        Tracker tracker = new Tracker();
        tracker.addIncome(new Income(LocalDate.now(), 10000, "Type 1", "Income No. 1"));
        tracker.addExpense(new Expense(LocalDate.now(), 499, "Type 2", "Expense No. 1"));
        String result = tracker.getExpenseList().get(0).getTransactionType();
        assertEquals("Type 2", result);
    }
}