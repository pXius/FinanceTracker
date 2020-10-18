package com.sda8.financetracker.transactions;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class to serve as a base for transaction type objects. ie. Income, Expenses, etc.
 * The class contains setters and getters for all fields except serialVersionUID which is set
 * to create reliable read and writes when the object is serialized.
 */
public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 744322561565640084L;
    private LocalDate date;
    private double transactionValue;
    private String transactionDescription;
    private String transactionType;

    /**
     * @param date (required) to set transaction date. Must be in LocalDate format.
     * @param transactionValue (required) value of transaction in type int.
     * @param transactionType (required) classification of transaction, preferably a single phrase.
     * @param transactionDescription (required) longer description of transaction.
     */
    public Transaction(LocalDate date, double transactionValue, String transactionType, String transactionDescription) {
        this.date = date;
        this.transactionValue = transactionValue;
        this.transactionDescription = transactionDescription;
        this.transactionType = transactionType;
    }

    /**
     * Get date field value.
     * @return the date field value in LocalDate format.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get date field value in string readable format.
     * @return the date field in readable string format. dd MMM yyyy
     */
    public String getDateString() {
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }

    /**
     * Sets the date field.
     * @param date needs to be LocalDate type.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get transaction field value.
     * @return transactionValue field value.
     */
    public double getTransactionValue() {
        return transactionValue;
    }

    /**
     * Compares transaction value argument to the field transactionValue value, sets the field
     * value to the argument value and returns the difference.
     * @param transactionValue of type double. Will be the used to set transactionValue field.
     * @return difference in type double between parameter and transactionValue field.
     */
    public double setTransactionValue(double transactionValue) {
        double adjustmentDifference = transactionValue - this.transactionValue;
        this.transactionValue = transactionValue;
        return adjustmentDifference;
    }

    /**
     * Sets the transactionType field to the string value of the parameter.
     * @param transactionType String value of the transaction type.
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Get transaction description in string format.
     * @return transaction description field in string format.
     */
    public String getTransactionDescription() {
        return transactionDescription;
    }

    /**
     * Get transaction type in string format.
     * @return transaction type field in string format.
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the transactionDescription field to the string value of the parameter.
     * @param transactionDescription String value of the transaction description.
     */
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    /**
     * Returns string representation of Transaction object field values.
     * @return string format representation of the transaction object, showing Date, Amount, Description and Type.
     */
    @Override
    public String toString() {
        return "\tDate: " + date +
                "\n\tAmount: " + transactionValue +
                "\n\tDescription : " + transactionDescription +
                "\n\tType: " + transactionType +
                "\n";
    }
}
