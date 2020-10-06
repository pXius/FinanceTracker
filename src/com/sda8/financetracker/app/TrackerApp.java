package com.sda8.financetracker.app;

import com.sda8.financetracker.datastorage.Storage;
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
    private boolean running;

    public TrackerApp() {
        this.trackerCore = Storage.checkFile() ? Storage.restoreData() : new Tracker();
        this.input = new UiInput();
        this.running = true;
        UiText.welcome();
    }

    public void run(){
        while (running) {
            mainMenu();
        }
    }

    public void mainMenu() {
        int selectedOption = input.numberInput(5, UiText::mainMenu);
        switch (selectedOption) {
            case 1 -> checkBalance();
            case 2 -> newTransaction();
            case 3 -> transactionHistory();
            case 4 -> {}
            case 5 -> saveAndExit();
        }
    }

    public void checkBalance() {
        UiText.clearScreen();
        trackerCore.printBalance();
        int selectedOption = input.numberInput(2, UiText::checkBalanceMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> saveAndExit();
        }
    }

    public void newTransaction() {
        UiText.clearScreen();
        int selectedOption = input.numberInput(3, UiText::newTransactionMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> trackerCore.addExpense((Expense) generateTransaction("expense"));
            case 3 -> trackerCore.addIncome((Income) generateTransaction("income"));
        }
    }

    public void transactionHistory(){
        UiText.clearScreen();

    }

    /*
    * Query User to input required information to create a income or expense transaction.
    * Return type Expense or Income determined by parameter "expenseOrIncome".
    * */
    public Transaction generateTransaction(String expenseOrIncome) {
        //Request all variables from console required by Expense or Income constructor.
        double transactionValue = input.amountInput(UiText::transactionAmount);
        String transactionDescription = input.textInput(UiText::transactionDescriptionText);
        String transactionType = input.textInput(UiText::transactionTypeText);
        LocalDate transactionDate = input.dateInput();
        //Determine the type of Transaction object to create and return.
        if (expenseOrIncome.equals("expense")) {
            return new Expense(transactionDate, transactionValue, transactionType, transactionDescription);
        } else {
            return new Income(transactionDate, transactionValue, transactionType, transactionDescription);
        }
    }

    public void saveAndExit() {
        Storage.saveData(trackerCore);
        this.running = false;
    }
}
