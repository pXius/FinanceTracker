package com.sda8.financetracker.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UiInput {
    private final Scanner scanner = new Scanner(System.in);

    /*
     * Take a numeric input from the console between 0 and inclusive of inputOptions parameter.
     * Console request text will be based on UiTextCall parameter.
     * */
    public int numberInput(int inputOptions, Runnable UiTextCall) {
        int selectedOption = 0;
        while (selectedOption == 0) {
            try {
                UiTextCall.run();
                selectedOption = scanner.nextInt();
                scanner.nextLine();
                if (selectedOption > 0 && selectedOption <= inputOptions) {
                    UiText.clearScreen();
                    break;
                } else {
                    selectedOption = 0;
                    UiText.invalidInput();
                }
            } catch (InputMismatchException e) {
                UiText.invalidInput();
                scanner.nextLine();
            }
        }
        return selectedOption;
    }

    /*
     * Retrieve and double value from console.
     * Console request text will be based on consoleRequestText parameter.
     * */
    public double amountInput(Runnable uiTextCall) {
        double amount = 0;
        while (amount <= 0) {
            try {
                uiTextCall.run();
                amount = scanner.nextDouble();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                UiText.invalidInput();
                scanner.nextLine();
            }
        }
        return amount;
    }

    /*
     * Generate LocalDate object based on console input.
     * Console request text will be based on UiText.dateInputText() from the UiText class.
     * */
    public LocalDate dateInput() {
        LocalDate date = null;
        while (date == null) {
            try {
                UiText.dateInputText();
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    date = DateTimeFormatter.ofPattern("dd-MM-yyyy").parse(input, LocalDate::from);
                }
            } catch (DateTimeParseException e) {
                UiText.invalidInput();
            }
        }
        return date;
    }

    /*
     * Retrieve and String value from console.
     * Console request text will be based on consoleRequestText parameter.
     * */
    public String textInput(Runnable uiTextCall) {
        String returnText = "";
        while (returnText.length() == 0) {
            uiTextCall.run();
            returnText = scanner.nextLine();
            if (returnText.length() == 0)
                UiText.invalidInput();
        }
        return returnText;
    }
}
