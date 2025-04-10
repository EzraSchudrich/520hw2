package filter;

import model.Transaction;
import controller.InputValidation;

import java.util.ArrayList;
import java.util.List;

public class AmountFilter implements TransactionFilter {
    private double amount = -1;

    public AmountFilter(double amount) {
        if (InputValidation.isValidAmount(amount))
            this.amount = amount;
    }

    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> filtered = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getAmount() == amount) {
                filtered.add(t);
            }
        }
        return filtered;
    }
}