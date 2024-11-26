package com.expense;

import model.Category;
import model.Expense;
import service.BudgetService;
import service.ExpenseService;
import service.UserService;
import utils.ReportManager;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        UserService userService = new UserService();
        ExpenseService expenseService = new ExpenseService();
        BudgetService budgetService = new BudgetService();
        ReportManager reportManager = new ReportManager();
        Scanner scanner = new Scanner(System.in);

        // User authentication
        System.out.println("Welcome to the Expense Tracker!");
        System.out.println("Do you have an account? (yes/no)");
        String response = scanner.nextLine().trim();

        if (response.equalsIgnoreCase("no")) {
            System.out.print("Enter a username to register: ");
            String username = scanner.nextLine();
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            if (userService.registerUser(username, password)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Username already exists. Please try again.");
                return;
            }
        }

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (!userService.loginUser(username, password)) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Login successful!");

        while (true) {
            System.out.println("\nEnter command (add, list, delete, summary, summary-month, set-budget, filter, export, or exit):");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add":
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter category (e.g., Food, Rent, Utilities): ");
                    String categoryName = scanner.nextLine();
                    Category category = new Category(categoryName);
                    expenseService.addExpense(description, amount, category);
                    break;

                case "list":
                    expenseService.listExpenses();
                    break;

                case "delete":
                    System.out.print("Enter expense ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    expenseService.deleteExpense(id);
                    break;

                case "summary":
                    expenseService.showSummary();
                    break;

                case "summary-month":
                    System.out.print("Enter month (1-12): ");
                    int month = Integer.parseInt(scanner.nextLine());
                    expenseService.showSummaryByMonth(month);
                    break;

                case "set-budget":
                    System.out.print("Enter monthly budget amount: ");
                    double budget = Double.parseDouble(scanner.nextLine());
                    budgetService.setMonthlyBudget(budget);
                    System.out.println("Monthly budget set successfully.");
                    break;

                case "filter":
                    System.out.println("Filter by category or date range? (category/date)");
                    String filterType = scanner.nextLine().trim();
                    if (filterType.equalsIgnoreCase("category")) {
                        System.out.print("Enter category to filter by: ");
                        String filterCategory = scanner.nextLine();
                        List<Expense> expensesByCategory = expenseService.filterExpensesByCategory(filterCategory);
                        if (expensesByCategory.isEmpty()) {
                            System.out.println("No expenses found for the specified category.");
                        } else {
                            expensesByCategory.forEach(expense ->
                                    System.out.println("ID: " + expense.getId() + " | Date: " + expense.getDate() +
                                            " | Description: " + expense.getDescription() +
                                            " | Amount: $" + expense.getAmount() +
                                            " | Category: " + expense.getCategory().getName()));
                        }
                    } else if (filterType.equalsIgnoreCase("date")) {
                        // Existing code for filtering by date
                    } else {
                        System.out.println("Invalid filter option.");
                    }
                    break;


                case "export":
                    System.out.print("Enter file path for CSV export (e.g., reports/expenses.csv): ");
                    String filePath = scanner.nextLine();
                    List<?> allExpenses = expenseService.getAllExpenses();
                    reportManager.exportToCSV((List<Expense>) allExpenses, filePath);
                    System.out.println("Expenses exported successfully to " + filePath);
                    break;

                case "exit":
                    System.out.println("Exiting the application.");
                    return;

                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}
