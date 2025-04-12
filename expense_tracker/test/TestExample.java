// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.Transient;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

import filter.AmountFilter;
import filter.CategoryFilter;

public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }
    


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testInvalidInput() {
        // Pre-condition: List of transactions is empty, transaction cost none
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.00, getTotalCost(), 0.01); //dont know if this needs to be model.get...

        // Perform the action: Add a transaction
        assertTrue(!controller.addTransaction(1001.00, "food")); 
        //NEED TO CHECK FOR EROR MESSAGE like in testexample

        // Post-condition: List of transactions and total cost remains unchanged
        assertEquals(0, model.getTransactions().size());
        assertEquals(0.00, getTotalCost(), 0.01); 
    }

    @Test
    public void testFilterByAmount() {
        // Pre-condition: List of transactions is empty, transaction cost none
        assertEquals(0, model.getTransactions().size());

        Transaction addedTransaction = new Transaction(30.00, "food");
        Transaction addedTransaction_2 = new Transaction(150.00, "travel");
        Transaction addedTransaction_3 = new Transaction(30.00, "other");

        model.addTransaction(addedTransaction);
        model.addTransaction(addedTransaction_2); //add transactions many times
        model.addTransaction(addedTransaction_3);
        AmountFilter filt = new AmountFilter(30.00);// i cant XD
        List<Transaction> filtered = filt.filter(model.getTransactions());

        //checking list is correct
        assertEquals(2, filtered.size());

        double totalCost = 0;
        for (Transaction transaction : filtered) {// use loop
            totalCost += transaction.getAmount();
        }
        assertEquals(totalCost, 60.00, 0.01);
        //check if cost is correct ts)
    }

    @Test
    public void testFilterByCategory() {
        // Pre-condition: List of transactions is empty, transaction cost none
        assertEquals(0, model.getTransactions().size());

        Transaction addedTransaction = new Transaction(30.00, "food");
        Transaction addedTransaction_2 = new Transaction(150.00, "travel");
        Transaction addedTransaction_3 = new Transaction(35.00, "food");

        model.addTransaction(addedTransaction);
        model.addTransaction(addedTransaction_2); //add transactions a lot
        model.addTransaction(addedTransaction_3);
        CategoryFilter filt = new CategoryFilter("food");// i cannnot loll
        List<Transaction> filtered = filt.filter(model.getTransactions());

        //checking list is correct here
        assertEquals(2, filtered.size()); // right here

        double totalCost = 0;
        for (Transaction transaction : filtered) {// use loop
            totalCost += transaction.getAmount();
        }
        assertEquals(totalCost, 65.00, 0.01);
        //check if cost is correct ts (65)
    }

}