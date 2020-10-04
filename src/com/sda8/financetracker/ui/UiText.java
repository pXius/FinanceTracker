package com.sda8.financetracker.ui;

public class UiText {

    public static void welcome() {
        System.out.println("Welcome to FinTrack!");
    }

    public static String mainMenu() {
        return """
                Please select an option:
                \t(1) Check Balance
                \t(2) New Transaction
                \t(3) Transaction History
                \t(4) Profile
                \t(5) Save and Exit
                """;
    }

    public static String checkBalanceMenu() {
        return """
                Please select an option:
                \t(1) <- Back to Main Menu
                \t(2)    Save and Exit
                """;
    }

    public static String newTransactionMenu() {
        return """
                Please select an option:
                \t(1) <- Back to Main Menu
                \t(2)    Add new Expense
                \t(3)    Add new Income
                """;
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

    public static String dateInputText() {
        return  """
                Enter a date:
                dd-mm-yyyy | ie. 14-06-2020
                """;
    }

    public static String transactionDescriptionText(){
        return  """
                Enter a description for this transaction:
                """;
    }
    public static String transactionTypeText(){
        return  """
                What Type of transaction is this?
                ie. Entertainment, Transport, Medical
                """;
    }

    public static String transactionAmount(){
        return  """
                Enter the transaction amount: 
                """;
    }

}
