package com.sda8.financetracker.app;

import com.sda8.financetracker.trackercore.Tracker;
import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.UiInput;
import com.sda8.financetracker.ui.UiText;

import java.time.LocalDate;

public class TrackerApp {
    private final Tracker trackerCore;
    private final UiInput input;

    public TrackerApp() {
        this.trackerCore = new Tracker();
        this.input = new UiInput();
        UiText.welcome();
    }

    public void mainMenu() {
        int selectedOption = input.numberInput(5, UiText.mainMenu());
        switch (selectedOption) {
            case 1:
                checkBalance();
                break;
            case 2:
                newTransaction();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    public void checkBalance() {
        UiText.clearScreen();
        trackerCore.printBalance();
        int selectedOption = input.numberInput(2, UiText.checkBalanceMenu());
        switch (selectedOption) {
            case 1:
                mainMenu();
                break;
            case 2:
                break;
        }
    }

    public void newTransaction() {
        UiText.clearScreen();
        int selectedOption = input.numberInput(3, UiText.newTransactionMenu());
        switch (selectedOption) {
            case 1 -> mainMenu();
            case 2 -> trackerCore.addExpense((Expense) generateTransaction("expense"));
            case 3 -> trackerCore.addIncome((Income) generateTransaction("income"));
        }
    }

    /*
    * Query User to input required information to create a income or expense transaction.
    * Return type Expense or Income determined by parameter "expenseOrIncome".
    * */
    public Transaction generateTransaction(String expenseOrIncome) {
        double amount = input.amountInput(UiText.transactionAmount());
        String description = input.textInput(UiText.transactionDescriptionText());
        String type = input.textInput(UiText.transactionTypeText());
        LocalDate date = input.dateInput();
        if (expenseOrIncome.equals("expense")) {
            return new Expense(date, amount, type, description);
        } else {
            return new Income(date, amount, type, description);
        }
    }
}
