package com.sda8.financetracker.app;

import com.diogonunes.jcolor.Attribute;
import com.sda8.financetracker.datastorage.Storage;
import com.sda8.financetracker.trackercore.TrackerCore;
import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;
import com.sda8.financetracker.transactions.Transaction;
import com.sda8.financetracker.ui.UiColumns;
import com.sda8.financetracker.ui.UiInput;
import com.sda8.financetracker.ui.UiText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * TrackerApp is a main class in the sense that it is responsible for all method calls, Ui navigation
 * and general implementation of all the features available in other classes. It is the heart of the
 * Fintrack app, the central control room.
 */
public class TrackerApp {
    private final TrackerCore trackerCore;
    private final UiInput input;
    private boolean running;

    /**
     * The TrackerApp needs three main components to function. A TrackerCore, a UiInput object and a running flag
     * to indicate the state of the program.
     * The trackerCore can be saved and restored from the Storage class. The constructor will attempt to restore
     * a trackerCore object on initialization.
     * A Ui Input object is created to to handle all console inputs.
     * The app running state is set to false by default.
     */
    public TrackerApp() {
        this.trackerCore = Storage.checkFile() ? Storage.restoreData() : new TrackerCore();
        this.input = new UiInput();
        this.running = false;
    }

    /**
     * The run method starts the program and initializes the loop and initial flow of the app.
     * A welcome text is printed and the main menu is called to the console.
     */
    public void run() {
        this.running = true;
        UiText.clearScreen();
        UiText.welcome();
        while (running) {
            mainMenu();
        }
    }

    /**
     * The mainMenu method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * This is the main entry point to the program and will always be the base state
     * for as long as the running field value is set to true.
     */
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

    /**
     * The checkBalance method prints the balance value from the trackerCore to the console
     * and gives the user the option to Save and exit or return back to the main menu.
     */
    public void checkBalance() {
        UiText.clearScreen();
        printBalance(trackerCore.getBalance());
        int selectedOption = input.numberInput(2, UiText::checkBalanceMenu);
        switch (selectedOption) {
            case 1 -> {}
            case 2 -> saveAndExit();
        }
    }

    /**
     * The newTransaction method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * The menu serves as the entry point for the user to create new transactions.
     */
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

    /**
     * The transactionHistory method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * The menu serves as the entry point for the user to view transaction history.
     */
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

    /**
     * The transactionHistory method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * The menu gives the user the ability to see the last 30 days of transaction
     * history or a custom period.
     */
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

    /**
     * This method is an extension of the History Generator.
     * It gets a start and end date from the console user in order to sort and filter a
     * transaction list by custom date period.
     * @param transactionList the list to be filtered by the custom date period.
     */
    public void customDateSelection(List<? extends Transaction> transactionList) {
        UiText.clearScreen();
        LocalDate startDate = input.dateInput(UiText::startDateText);
        LocalDate endDate = todayOrCustomDate(UiText::endDateText);
        transactionHistorySortBy(trackerCore.filterListByDate(transactionList, startDate, endDate));
    }

    /**
     * This method serves a menu to the console giving the user the choice to perform their desired action
     * with the current date or custom date when working with Transaction objects and lists.
     * @param textForDateInput a void print method that will be used to display printed instruction
     *                         to a console user when a custom date is requested.
     * @return returns the date to be used. Now or custom.
     */
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

    /**
     * The transactionHistorySortBy method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * This menu serves to give the console user the option to sort a list of transactions
     * by date, value, description or type. The choice will be printed to console in the desired order.
     * Finally a editOrDelete method is called giving the user an option to edit or delete any of the displayed
     * transactions.
     * @param transactionList The list that will be printed to console in the selected sorting order.
     */
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

    /**
     * This method is called everytime a new transaction object needs to be created.
     * The method will query the user to input required information to create a income or expense transaction.
     * Each of the input calls will be saved to a variable and passed to the appropriate transaction constructor.
     * Return type Expense or Income determined by parameter "expenseOrIncome".
     * @param expenseOrIncome string variable determining whether the Expense or Income constructor is called.
     *                        The value "expense" will pass the variables to the Expense constructor.
     *                        Any other value will pass the variables to the income constructor.
     * @return returns a Transaction object from either the Expense or Income constructor.
     */
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

    /**
     * printTransactions takes a list of Transaction objects, if the list is empty, it warns the user.
     * If there are Transaction objects within the list, the method will pass the lists to the columnGenerator
     * method which will colourise, space and stringify the objects. Once returned from the generator,
     * the list will be printed to the console.
     * @param transactionList the list of transactions to be printed.
     * @return the transaction list to be used by other method calls if required.
     */
    public List<Transaction> printTransactions(List<Transaction> transactionList) {
        if (transactionList.size() == 0) {
            UiText.noSuchTransactions();
        } else {
            columnGenerator(transactionList).print();
        }
        return transactionList;
    }

    /**
     * Column generator takes a list of Transaction objects and formats it into table format. Expense objects have
     * their values coloured to red. Income objects have their values coloured to green. All other string values
     * in the object are spaced evenly based on the widest string value.
     * The Method returns a string of evenly spaced columns.
     * @param transactionList List of transaction to be formatted in evenly spaced columns in strings.
     * @return the list of objects in table String type format. Ready to be printed to console.
     */
    public UiColumns columnGenerator(List<Transaction> transactionList) {
        UiColumns uiColumns = new UiColumns();
        uiColumns.addLine("No.", "\tDate", "\tType", "\tDescription", "\t" + String.format("%14s", "Value"));
        uiColumns.addLine("", "", "", "", "");
        transactionList.forEach(transaction -> {
            String transactionValue = transaction instanceof Expense ?
                    colorize(String.format("%,14.2f", (transaction.getTransactionValue() * -1)), Attribute.BRIGHT_RED_TEXT()) :
                    colorize(String.format("%,14.2f", transaction.getTransactionValue()), Attribute.BRIGHT_GREEN_TEXT());
            uiColumns.addLine(
                    String.format("%d", transactionList.indexOf(transaction) + 1),
                    "\t" + transaction.getDateString(),
                    "\t" + transaction.getTransactionType(),
                    "\t" + transaction.getTransactionDescription(),
                    "\t" + transactionValue);
        });
        return uiColumns;
    }

    /**
     *  Prints the current balance field value to the console.
     * @param balance double value to be displayed in console.
     */
    public void printBalance(double balance) {
        String textBalance = balance > 0 ?
                colorize(String.format("%.2f", balance), Attribute.BRIGHT_GREEN_TEXT()) :
                colorize(String.format("%.2f", balance), Attribute.BRIGHT_RED_TEXT());
        System.out.println("Your current balance is: " + textBalance + "\n");
    }

    /**
     * The transactionList method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * This menu gives the console user the choice to edit or delete any of the Transactions in
     * the printed list.
     * @param transactionList the list of objects that can be edited or deleted.
     */
    public void editOrDelete(List<Transaction> transactionList) {
        int selectedOption = input.numberInput(3, UiText::editDeleteMenu);
        switch (selectedOption) {
            case 1 -> {}
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

    /**
     * The editSelectMenu method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * This menu gives the console user the choice to edit specific field variables of a chosen
     * transaction. The method creates a loop condition and will continue to offer the user edit
     * choices until the the user is satisfied with the object field values.
     * Upon completing any editing, the trackerCore state is saved to an external file.
     * @param transaction the specific transaction object to be edited.
     */
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

    /**
     * The searchMenu method sets up a console menu and input object to cover all cases.
     * The uiText class is also called to display instructions to the console user.
     * This menu gives the console user the choice to search for a specific transaction or list of
     * transactions by keyword. The user can choose to search Expense, Income or both lists for
     * Transaction containing the keyword in either their type or description fields.
     * Upon finding matching transactions, they will be printed to the console and again offer
     * the option to edit or delete them.
     */
    public void searchMenu() {
        UiText.clearScreen();
        List<Transaction> searchedList = null;
        int selectedOption = input.numberInput(4, UiText::searchMenu);
        switch (selectedOption) {
            case 1 -> {}
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

    /**
     * deleteTransaction handles the removal of transaction objects from the tracker core. Regardless of the list,
     * history, filter, search or sorting method used to acquire the transaction to be deleted, it will be
     * deleted from the tracker core. The method takes a list of Transaction objects and gives the console
     * user a choice of which Transaction to delete.
     * Upon successful deletion of an object, the balance of the tracker core will be adjusted with the transaction
     * value. The tracker core will also be saved on successful deletion of a Transaction object.
     * @param transactionList list of Transaction object of which an object will be deleted.
     */
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

    /**
     * saveAndExit, does what it says on the tin. Saves the tracker core to an external file and sets
     * the program running state to false. This will cause the app to exit its running loop and terminate.
     */
    public void saveAndExit() {
        Storage.saveData(trackerCore);
        this.running = false;
    }
}
