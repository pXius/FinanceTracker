package com.sda8.financetracker.ui;

public class UiText {

    public static void welcome() {
        System.out.println("""
                                
                ███████╗██╗███╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗
                ██╔════╝██║████╗  ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
                █████╗  ██║██╔██╗ ██║   ██║   ██████╔╝███████║██║     █████╔╝\s
                ██╔══╝  ██║██║╚██╗██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗\s
                ██║     ██║██║ ╚████║   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗
                ╚═╝     ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                """);
    }

    public static void mainMenu() {
        System.out.println("""
                Please select an option:
                \t(1) Check Balance
                \t(2) New Transaction
                \t(3) Transaction History
                \t(4) Transaction Search (Not Working Yet)
                \t(5) Save and Exit
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
                \t(1) <- Back to Main Menu
                                            
                \t       Sort by...
                \t(2)    Date --> Newest First
                \t(3)    Date --> Oldest First
                                            
                \t(4)    Transaction Value --> Highest First
                \t(5)    Transaction Value --> Lowest First
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
                \t(3)    Delete Transaction (Not Working Yet)
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

    public static void enterTransactionNumber() {
        System.out.println("Enter Transaction Number: ");
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
                ie. Entertainment, Transport, Medical
                """);
    }

    public static void transactionAmount() {
        System.out.println("Enter the transaction amount: \n");
    }

    public static void balanceText(double balance) {
        System.out.printf("Your current balance is: %.2f\n", balance);
    }


    public static void invalidInput() {
        UiText.clearScreen();
        System.out.println("Warning: Invalid Input!\n");
    }

    public static void clearScreen() {
        //Clear Java Console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
