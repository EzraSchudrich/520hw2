package controller;

import view.ExpenseTrackerView;

import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;

public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    view.getFilterButton().addActionListener(e -> {
      // Get transaction data from view
      String type = view.getFilterType();
      String input = view.getFilterInput();
      List<Transaction> transactions = model.getTransactions();
      boolean filtered = false;

      // filters transactions based on filter type
      if (type.equals("Amount <")) {
        int numInput = Integer.parseInt(input);

        if (InputValidation.isValidAmount(numInput)) {
          // only apply filter if input is valid
          removeAllTransactions();

          for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getAmount() < numInput)
              model.addTransaction(transactions.get(i));
          }

          filtered = true;
        }

      } else {
        if (InputValidation.isValidCategory(input)) {
          removeAllTransactions();

          for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getCategory().equals(input))
              model.addTransaction(transactions.get(i));
          }

          filtered = true;
        }
      }

      refresh();
      if (!filtered) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
    refresh();
    return true;
  }

  private void removeAllTransactions() {
    List<Transaction> transactions = model.getTransactions();
    for (int i = 0; i < transactions.size(); i++) {
      model.removeTransaction(transactions.get(i));
    }
  }
}
