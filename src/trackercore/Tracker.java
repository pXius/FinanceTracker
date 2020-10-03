package trackercore;

import com.sda8.financetracker.transactions.Expense;
import com.sda8.financetracker.transactions.Income;

import java.util.ArrayList;

public class Tracker {
    private double balance;
    private ArrayList<Expense> expenseList;
    private ArrayList<Income> incomeList;

    public Tracker(double balance) {
        this.balance = balance;
        expenseList = new ArrayList<>();
        incomeList = new ArrayList<>();
    }
}
