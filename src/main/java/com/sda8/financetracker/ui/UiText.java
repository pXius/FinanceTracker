package com.sda8.financetracker.ui;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The UiText class exists to serve static print methods to all other classes. These print methods
 * communicate all console instructions to the console user.
 * This class is also responsible for all other UiText elements used to navigate the
 * text based interface. All methods are void, all methods print to console.
 */
public class UiText {

    public static void welcome() {
        System.out.println(colorize("""
                                
                ███████╗██╗███╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗
                ██╔════╝██║████╗  ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
                █████╗  ██║██╔██╗ ██║   ██║   ██████╔╝███████║██║     █████╔╝\s
                ██╔══╝  ██║██║╚██╗██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗\s
                ██║     ██║██║ ╚████║   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗
                ╚═╝     ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                """, Attribute.BRIGHT_CYAN_TEXT()));
        System.out.println(colorize("Please expand your terminal for the best experience.\n", Attribute.BRIGHT_MAGENTA_TEXT()));
    }

    public static void mainMenu() {
        System.out.println("""
                Please select an option:
                \t(1) 💰 Check Balance 
                \t(2) 📝 New Transaction 
                \t(3) ⏳ Transaction History 
                \t(4) 🔍 Transaction Search 
                \t(5) 💾 Save and Exit 
                """);
    }

    public static void checkBalanceMenu() {
        System.out.println("""
                Please select an option:
                \t(1) <- Back to Main Menu
                \t(2)    Save and Exit
                """);
    }

    public static void newTransactionMenu() {
        System.out.println("""
                Please select an option:
                \t(1) <- Back to Main Menu
                \t(2)    Add new Expense
                \t(3)    Add new Income
                """);
    }

    public static void transactionHistoryMenu() {
        System.out.println("""
                Please select an option:
                \t(1) <- Back to Main Menu
                \t(2)    Transaction History
                \t(3)    Income History
                \t(4)    Expense History
                """);
    }

    public static void dateSelectionMenu() {
        System.out.println("""
                Please select an option:
                \t(1) <- Back to Transaction History
                \t(2)    Last 30 days
                \t(3)    Custom Period...
                """);
    }

    public static void sortByMenu() {
        System.out.println("""
                Please select an option:          
                \tSort by...
                \t(1)    Date --> Newest First
                \t(2)    Date --> Oldest First
                                            
                \t(3)    Transaction Value --> Highest First
                \t(4)    Transaction Value --> Lowest First
                                
                \t(5)    Description --> Alphabetical
                \t(6)    Type --> Alphabetical
                """);
    }

    public static void todayOrCustomDate() {
        System.out.println("""
                Please select an option:
                \t(1)    Today
                \t(2)    Other Date...
                """);
    }

    public static void editDeleteMenu() {
        System.out.println("""
                Please select an option:
                \t(1) <- Done
                \t(2)    Edit Transaction
                \t(3)    Delete Transaction
                """);
    }

    public static void editSelectMenu() {
        System.out.println("""
                What would you like to edit?
                Please select an option:
                \t(1) <- Done
                \t(2)    Edit Value
                \t(3)    Edit Description
                \t(4)    Edit Type
                \t(5)    Edit Date
                """);
    }

    public static void transactionToEdit() {
        System.out.println("Enter transaction number to edit: ");
    }

    public static void searchMenu() {
        System.out.println("""
                What type of transactions would you like to search?
                Please select an option:
                \t(1) <- Done
                \t(2)    Income Only
                \t(3)    Expenses Only
                \t(4)    Both
                """);
    }

    public static void transactionDateMenu() {
        System.out.println("Transaction Date: ");
    }

    public static void transactionDateText() {
        System.out.println("""
                Enter transaction date date:
                dd-mm-yyyy | ie. 14-06-2020
                """);
    }

    public static void startDateText() {
        System.out.println("""
                Enter a start date:
                dd-mm-yyyy | ie. 14-06-2020
                """);
    }

    public static void endDateText() {
        System.out.println("""
                Enter an end date:
                dd-mm-yyyy | ie. 14-06-2020
                """);
    }

    public static void transactionDescriptionText() {
        System.out.println("""
                Enter a description for this transaction:
                """);
    }

    public static void transactionTypeText() {
        System.out.println("""
                What Type of transaction is this?
                ie. Entertainment, Transport, Medical, Income, etc.
                """);
    }

    public static void transactionAmount() {
        System.out.println("Enter the transaction amount: \n");
    }

    public static void balanceText(double balance) {
        String textBalance = balance > 0 ?
                colorize(String.format("%.2f", balance), Attribute.BRIGHT_GREEN_TEXT()) :
                colorize(String.format("%.2f", balance), Attribute.BRIGHT_RED_TEXT());
        System.out.println("Your current balance is: " + textBalance + "\n");
    }

    public static void deleteSuccessful() {
        System.out.println("Transaction Successfully Deleted!\n");
    }

    public static void deleteFailed() {
        System.out.println("Transaction could not be Deleted!\n");
    }

    public static void deleteSelectText() {
        System.out.println("Enter transaction number to delete: ");
    }

    public static void noSuchTransactions() {
        System.out.println("No transactions matching your search criteria...\n");
    }

    public static void transactionAdded() {
        System.out.println("Transaction successfully added.\n");
    }

    public static void invalidInput() {
        UiText.clearScreen();
        System.out.println("⚠️ Invalid Input!\n");
    }

    public static void searchKeywordText() {
        System.out.println("Please enter a search phrase or keyword...\n");
    }

    public static void clearScreen() {
        //Clear Java Console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}