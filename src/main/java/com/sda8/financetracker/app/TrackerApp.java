package com.sda8.financetracker.app;

import com.diogonunes.jcolor.Attribute;
import com.sda8.financetracker.datastorage.Storage;
import com.sda8.financetracker.trackercore.Tracker;
import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.Columns;
import com.sda8.financetracker.ui.UiInput;
import com.sda8.financetracker.ui.UiText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

public class TrackerApp {
    private final Tracker trackerCore;
    private final UiInput input;
    private boolean running;

    public TrackerApp() {
        this.trackerCore = Storage.checkFile() ? Storage.restoreData() : new Tracker();
        this.input = new UiInput();
        this.running = true;
    }

    public void run() {
        UiText.clearScreen();
        UiText.welcome();
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
            case 4 -> searchMenu();
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
            case 1 -> {
            }
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
            case 1 -> {
            }
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
            case 1 -> {
            }
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
        List<Transaction> sortedList = null;
        UiText.clearScreen();
        int selectedOption = input.numberInput(7, UiText::sortByMenu);
        switch (selectedOption) {
            case 1 -> sortedList = printTransactions(trackerCore.sortByDate(transactionList, true));
            case 2 -> sortedList = printTransactions(trackerCore.sortByDate(transactionList, false));
            case 3 -> sortedList = printTransactions(trackerCore.sortByTransactionValue(transactionList, true));
            case 4 -> sortedList = printTransactions(trackerCore.sortByTransactionValue(transactionList, false));
            case 5 -> sortedList = printTransactions(trackerCore.sortByDescription(transactionList));
            case 6 -> sortedList = printTransactions(trackerCore.sortByType(transactionList));
        }
        editOrDelete(sortedList);
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
        try {
            if (expenseOrIncome.equals("expense")) {
                return new Expense(transactionDate, transactionValue, transactionType, transactionDescription);
            } else {
                return new Income(transactionDate, transactionValue, transactionType, transactionDescription);
            }
        } finally {
            UiText.transactionAdded();
        }
    }

    public List<Transaction> printTransactions(List<Transaction> transactionList) {
        if (transactionList.size() == 0) {
            UiText.noSuchTransactions();
        } else {
            columnGenerator(transactionList).print();
        }
        return transactionList;
    }

    public Columns columnGenerator(List<Transaction> transactionList) {
        Columns columns = new Columns();
        columns.addLine("No.", "\tDate", "\tType", "\tDescription", "\t" + String.format("%14s", "Value"));
        columns.addLine("", "", "", "", "");
        transactionList.forEach(transaction -> {
            String transactionValue = transaction instanceof Expense ?
                    colorize(String.format("%,14.2f", (transaction.getTransactionValue() * -1)), Attribute.BRIGHT_RED_TEXT()) :
                    colorize(String.format("%,14.2f", transaction.getTransactionValue()), Attribute.BRIGHT_GREEN_TEXT());
            columns.addLine(
                    String.format("%d", transactionList.indexOf(transaction) + 1),
                    "\t" + transaction.getDateString(),
                    "\t" + transaction.getTransactionType(),
                    "\t" + transaction.getTransactionDescription(),
                    "\t" + transactionValue);
        });
        return columns;
    }

    public void editOrDelete(List<Transaction> transactionList) {
        int selectedOption = input.numberInput(3, UiText::editDeleteMenu);
        switch (selectedOption) {
            case 1 -> {
            }
            case 2 -> {
                printTransactions(transactionList);
                editSelectMenu(transactionList.get(input.numberInput(transactionList.size(),
                        UiText::transactionToEdit) - 1));
            }
            case 3 -> {
                printTransactions(transactionList);
                deleteTransaction(transactionList);
            }
        }
    }

    public void editSelectMenu(Transaction transaction) {
        List<Transaction> listToPrint = new ArrayList<>();
        listToPrint.add(transaction);
        boolean done = false;
        while (!done) {
            UiText.clearScreen();
            printTransactions(listToPrint);
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

    public void searchMenu() {
        UiText.clearScreen();
        List<Transaction> searchedList = null;
        int selectedOption = input.numberInput(4, UiText::searchMenu);
        switch (selectedOption) {
            case 1 -> {
            }
            case 2 -> {
                searchedList = trackerCore.filterByKeyword(trackerCore
                        .getIncomeList(), input
                        .textInput(UiText::searchKeywordText)
                        .toLowerCase());
                printTransactions(searchedList);
            }
            case 3 -> {
                searchedList = trackerCore.filterByKeyword(
                        trackerCore.getExpenseList(),
                        input.textInput(UiText::searchKeywordText).toLowerCase());
                printTransactions(searchedList);
            }
            case 4 -> {
                searchedList = trackerCore.filterByKeyword(
                        trackerCore.mergeTransactionList(
                                trackerCore.getExpenseList(),
                                trackerCore.getIncomeList()),
                        input.textInput(UiText::searchKeywordText).toLowerCase());
                printTransactions(searchedList);
            }
        }
        editOrDelete(searchedList);
    }

    public void deleteTransaction(List<Transaction> transactionList) {
        Transaction transactionToDelete = transactionList.get(
                input.numberInput(transactionList.size(), UiText::deleteSelectText) - 1);
        boolean deleteSuccess = trackerCore.deleteTransaction(transactionToDelete);
        if (deleteSuccess) {
            trackerCore.adjustBalance(transactionToDelete.getTransactionValue());
            UiText.deleteSuccessful();
            Storage.saveData(trackerCore);
        } else {
            UiText.deleteFailed();
        }
    }

    public void saveAndExit() {
        Storage.saveData(trackerCore);
        this.running = false;
    }
}