package filter;

import model.Transaction;
import controller.InputValidation;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilter implements TransactionFilter {
    private String category = "";

    public CategoryFilter(String category) {

        if (InputValidation.isValidCategory(category))
            this.category = category;

    }

    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory() == category) {
                filtered.add(t);
            }
        }
        return filtered;
    }
}