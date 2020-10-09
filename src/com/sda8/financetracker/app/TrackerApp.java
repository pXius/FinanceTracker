package com.sda8.financetracker.app;

import com.sda8.financetracker.datastorage.Storage;
import com.sda8.financetracker.trackercore.Tracker;
import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.UiInput;
import com.sda8.financetracker.ui.UiText;

import java.time.LocalDate;
import java.util.List;

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

    public void run() {
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
            case 4 -> {} //Coming Soon
            case 5 -> saveAndExit();
        }
    }

    public void checkBalance() {
        UiText.clearScreen();
        trackerCore.printBalance();
        int selectedOption = input.numberInput(2, UiText::checkBalanceMenu);
        switch (selectedOption) {
            case 1 -> {
            }
            case 2 -> saveAndExit();
        }
    }

    public void newTransaction() {
        UiText.clearScreen();
        int selectedOption = input.numberInput(3, UiText::newTransactionMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> {
                trackerCore.addExpense((Expense) generateTransaction("expense"));
                Storage.saveData(trackerCore);
            }
            case 3 -> {
                trackerCore.addIncome((Income) generateTransaction("income"));
                Storage.saveData(trackerCore);
            }
        }
    }

    public void transactionHistory() {
        UiText.clearScreen();
        int selectedOption = input.numberInput(4, UiText::transactionHistoryMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> transactionHistoryGenerator(trackerCore.mergeTransactionList(
                    trackerCore.getExpenseList(),
                    trackerCore.getIncomeList()));
            case 3 -> transactionHistoryGenerator(trackerCore.getIncomeList());
            case 4 -> transactionHistoryGenerator(trackerCore.getExpenseList());
        }
    }

    public void transactionHistoryGenerator(List<? extends Transaction> transactionList) {
        UiText.clearScreen();
        int selectedOption = input.numberInput(3, UiText::dateSelectionMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> transactionHistorySortBy(trackerCore.filterListByDate(
                    transactionList,
                    LocalDate.now().minusDays(30),
                    LocalDate.now()));
            case 3 -> customDateSelection(transactionList);
        }
    }

    public void customDateSelection(List<? extends Transaction> transactionList) {
        UiText.clearScreen();
        LocalDate startDate = input.dateInput(UiText::startDateText);
        LocalDate endDate = todayOrCustomDate(UiText::endDateText);
        transactionHistorySortBy(trackerCore.filterListByDate(transactionList, startDate, endDate));
    }

    public LocalDate todayOrCustomDate(Runnable textForDateInput) {
        UiText.clearScreen();
        int selectedOption = input.numberInput(2, UiText::todayOrCustomDate);
        LocalDate todayOrCustom = null;
        switch (selectedOption) {
            case 1 -> todayOrCustom = LocalDate.now();
            case 2 -> todayOrCustom = input.dateInput(textForDateInput);
        }
        return todayOrCustom;
    }

    public void transactionHistorySortBy(List<Transaction> transactionList) {
        UiText.clearScreen();
        int selectedOption = input.numberInput(5, UiText::sortByMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> printTransactions(trackerCore.sortByDate(transactionList, true));
            case 3 -> printTransactions(trackerCore.sortByDate(transactionList, false));
            case 4 -> printTransactions(trackerCore.sortByTransactionValue(transactionList, true));
            case 5 -> printTransactions(trackerCore.sortByTransactionValue(transactionList, false));
        }
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
        UiText.transactionDateMenu();
        LocalDate transactionDate = todayOrCustomDate(UiText::transactionDateText);
        //Determine the type of Transaction object to create and return.
        if (expenseOrIncome.equals("expense")) {
            return new Expense(transactionDate, transactionValue, transactionType, transactionDescription);
        } else {
            return new Income(transactionDate, transactionValue, transactionType, transactionDescription);
        }
    }

    public void printTransactions(List<Transaction> transactionList) {
        transactionList.forEach(transaction -> {
            System.out.printf("Transaction No: %d\n", transactionList.indexOf(transaction) + 1);
            System.out.println(transaction);
        });
        editOrDelete(transactionList);
    }

    public void editOrDelete(List<Transaction> transactionList) {
        int selectedOption = input.numberInput(3, UiText::editDeleteMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> editSelectMenu(transactionList,
                    transactionList.get(input.numberInput(transactionList.size(),
                            UiText::enterTransactionNumber) - 1));
            case 3 -> {} //coming soon to a branch near you.
        }
    }

    public void editSelectMenu(List<Transaction> transactionList, Transaction transaction) {
        boolean done = false;
        while (!done) {
            UiText.clearScreen();
            System.out.println(transaction);
            int selectedOption = input.numberInput(5, UiText::editSelectMenu);
            switch (selectedOption) {
                case 1 -> done = true;
                case 2 -> trackerCore.adjustBalance(transaction.setTransactionValue(input.amountInput(UiText::transactionAmount)));
                case 3 -> transaction.setTransactionDescription(input.textInput(UiText::transactionDescriptionText));
                case 4 -> transaction.setTransactionType(input.textInput(UiText::transactionTypeText));
                case 5 -> transaction.setDate(todayOrCustomDate(UiText::transactionDateText));
            }
            Storage.saveData(trackerCore);
        }
    }

    public void saveAndExit() {
        Storage.saveData(trackerCore);
        this.running = false;
    }
}
