package service;

import model.Category;
import model.Expense;
import utils.FileManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseService {
    private final FileManager fileManager;
    private List<Expense> expenses;
    private int nextId;
    private String currentMonth;

    public ExpenseService() {
        this.fileManager = new FileManager();
        Date currentDate = new Date();
        this.currentMonth = new SimpleDateFormat("yyyy-MMMM").format(currentDate);
        this.expenses = fileManager.loadExpensesForMonth(currentDate);
        // Set the nextId based on the maximum existing ID for the current month
        this.nextId = expenses.stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1; // If no expenses, start with ID 1
    }

    public void addExpense(String description, double amount, Category category) {
        Date currentDate = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        String newMonth = new SimpleDateFormat("yyyy-MMMM").format(currentDate);

        // Reset the ID if a new month has started
        if (!newMonth.equals(currentMonth)) {
            currentMonth = newMonth;
            nextId = 1;
            expenses = fileManager.loadExpensesForMonth(currentDate); // Load new month's expenses
        }

        // Ensure that the category is not null
        if (category == null || category.getName().trim().isEmpty()) {
            System.out.println("Invalid category. Please provide a valid category.");
            return;
        }

        // Create the new expense with the provided category
        Expense newExpense = new Expense(nextId++, description, amount, date, category);
        expenses.add(newExpense);
        fileManager.saveExpensesForMonth(expenses, currentDate);
        System.out.println("Expense added successfully (ID: " + newExpense.getId() + ")");
    }


    public void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("ID  | Date       | Description  | Amount");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public void deleteExpense(int id) {
        Expense expenseToDelete = null;
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenseToDelete = expense;
                break;
            }
        }
        if (expenseToDelete != null) {
            expenses.remove(expenseToDelete);
            fileManager.saveExpensesForMonth(expenses, new Date());
            System.out.println("Expense deleted successfully");
        } else {
            System.out.println("Expense not found.");
        }
    }

    public void showSummary() {
        // Load only the expenses for the current month
        List<Expense> currentMonthExpenses = fileManager.loadCurrentMonthExpenses();

        // Calculate the total expenses for the current month
        double total = currentMonthExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        System.out.println("Total expenses for the current month: $" + total);
    }



    public void showSummaryByMonth(int month) {
        try {
            // Get the current year to build the file name
            String currentYear = new SimpleDateFormat("yyyy").format(new Date());
            // Format the month number to have two digits (e.g., 1 -> "01")
            String formattedMonth = String.format("%02d", month);

            // Construct the filename using the current year and the formatted month name (e.g., "2024-October.json")
            SimpleDateFormat fullFormatter = new SimpleDateFormat("yyyy-MMMM");
            Date targetDate = fullFormatter.parse(currentYear + "-" + new SimpleDateFormat("MMMM").format(new SimpleDateFormat("MM").parse(formattedMonth)));

            // Load expenses for the requested month
            List<Expense> monthExpenses = fileManager.loadExpensesForMonth(targetDate);

            // Calculate the total for the requested month
            double total = monthExpenses.stream()
                    .mapToDouble(Expense::getAmount)
                    .sum();

            // Check if there were any expenses for the month
            if (total == 0) {
                System.out.println("No expenses recorded for the specified month.");
            } else {
                System.out.println("Total expenses for month " + month + ": $" + total);
            }
        } catch (Exception e) {
            System.out.println("Error processing month summary. Please enter a valid month.");
            e.printStackTrace();
        }
    }


    public Date parseDate(String dateStr) {
        try {
            // Use SimpleDateFormat to parse the date string in yyyy-MM-dd format
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            // Print an error message if the date is not valid and return null
            System.out.println("Invalid date format: " + dateStr + ". Please use yyyy-MM-dd.");
            return null;
        }
    }

    public List<Expense> filterExpensesByDateRange(Date startDate, Date endDate) {
        // If the start date or end date is null, print an error message and return an empty list
        if (startDate == null || endDate == null) {
            System.out.println("Invalid date range provided. Please make sure both start and end dates are valid.");
            return new ArrayList<>(); // Return an empty list for safety
        }

        // Filter expenses based on the provided date range
        return expenses.stream()
                .filter(expense -> {
                    // Parse the expense date from the stored string
                    Date expenseDate = parseDate(expense.getDate());
                    // Only include expenses whose date is between startDate and endDate
                    return expenseDate != null && !expenseDate.before(startDate) && !expenseDate.after(endDate);
                })
                .collect(Collectors.toList());
    }

    public List<Expense> filterExpensesByCategory(String categoryName) {
        // Check if the category name is valid (not null or empty)
        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("Invalid category name provided.");
            return new ArrayList<>(); // Return an empty list for safety
        }

        // Filter expenses based on the provided category name (ignoring case for comparison)
        return expenses.stream()
                .filter(expense -> expense.getCategory() != null &&
                        expense.getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }


    public List<Expense> getAllExpenses() {
        // If expenses list is null, initialize it as an empty list
        if (expenses == null) {
            expenses = new ArrayList<>();
        }
        return expenses;
    }

}
