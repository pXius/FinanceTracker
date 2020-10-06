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
        System.out.println( """
                            Please select an option:
                            \t(1) Check Balance
                            \t(2) New Transaction
                            \t(3) Transaction History
                            \t(4) Profile
                            \t(5) Save and Exit
                            """);
    }

    public static void checkBalanceMenu() {
        System.out.println( """
                            Please select an option:
                            \t(1) <- Back to Main Menu
                            \t(2)    Save and Exit
                            """);
    }

    public static void newTransactionMenu() {
        System.out.println( """
                            Please select an option:
                            \t(1) <- Back to Main Menu
                            \t(2)    Add new Expense
                            \t(3)    Add new Income
                            """);
    }

    public static void transactionHistoryMenu() {
        System.out.println( """
                            Please select an option:
                            \t(1) <- Back to Main Menu
                            \t(2)    Transaction History
                            \t(3)    Income History
                            \t(4)    Expense History
                            """);
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

    public static void dateInputText() {
        System.out.println( """
                            Enter a date:
                            dd-mm-yyyy | ie. 14-06-2020
                            """);
    }

    public static void transactionDescriptionText() {
        System.out.println( """
                            Enter a description for this transaction:
                            """);
    }

    public static void transactionTypeText() {
        System.out.println( """
                            What Type of transaction is this?
                            ie. Entertainment, Transport, Medical
                            """);
    }

    public static void transactionAmount() {
        System.out.println("Enter the transaction amount: ");
    }

    public static void balanceText(double balance) {
        System.out.printf("Your current balance is: %.2f\n", balance);
    }

}
