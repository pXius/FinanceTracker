package com.sda8.financetracker.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UiInput is designed to take console input of type LocalDate, double, int or String. Each input call can display
 * custom text instructions to the console user by means of Runnable method calls. Methods will handle exceptions
 * and incorrect input based on input type.
 * Methods will return inputs based on method return type
 */
public class UiInput {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Take a numeric input from the console between 0 and inclusive of inputOptions parameter.
     * Console request text will be based on UiTextCall parameter. ie. A method calling System.out.println
     * to print instructions would be a viable option.
     * @param inputOptions maximum accepted int value.
     * @param UiTextCall calls a method to print instructions to console.
     * @return the int value read from the console.
     */
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

    /**
     * Retrieve double value from console.
     * Console request text will be based on UiTextCall parameter.
     * ie. A method calling System.out.println to print instructions would be a viable option.
     * @param uiTextCall calls a method to print instructions to console.
     * @return returns double value read from the console.
     */
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

    /**
     * Generate LocalDate object based on console input of type String.
     * The String value will be converted to a LocalDate object.
     * Console request text will be based on UiTextCall parameter.
     * ie. A method calling System.out.println to print instructions would be a viable option.
     * @param uiTextCall calls a method to print instructions to console.
     * @return a LocalDate object
     */
    public LocalDate dateInput(Runnable uiTextCall) {
        LocalDate date = null;
        while (date == null) {
            try {
                uiTextCall.run();
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

    /**
     * Retrieve and String value from console.
     * Console request text will be based on UiTextCall parameter.
     * ie. A method calling System.out.println to print instructions would be a viable option.
     * @param uiTextCall calls a method to print instructions to console.
     * @return unmodified String input from console.
     */
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
