package com.sda8.financetracker.transactions;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Income class is a clone of the Transaction class, but serves as "Type Security"
 * The class is used to classify all Transactions of type Income.
 * Since Transaction is abstract, it needs to be initialized by an extending class.
 */
public class Income extends Transaction implements Serializable {
    public Income(LocalDate date, double transactionValue, String transactionType, String transactionDescription) {
        super(date, transactionValue, transactionType, transactionDescription);
    }

    /**
     * Returns string representation of Income object field values.
     * @return string format representation of the transaction object, showing Date, Amount, Description and Type.
     */
    @Override
    public String toString() {
        return "Income:\n" + super.toString();
    }
}
