# Expense Tracker

Expense Tracker is a command-line application built in Java to help users efficiently manage and track their daily expenses. It supports adding, updating, deleting, and listing expenses, and offers various summaries of expenses for the current month or a specified month. All expenses are stored persistently as JSON objects in files.

# Tech Stack

- Java: The primary programming language used for building the application.
- JSON: Used for storing and persisting expenses and user data in a structured format.
- Gson: A Java library used to convert Java objects to JSON and vice-versa.


## System Requirements

- Java 1.8 or higher
- Git (for cloning the repositiry)

## Features
- User Authentication: Register and log in with a username and password.
- Add Expense: Add a new expense with a description, amount, and category.
- Update Expense: Modify an existing expense’s description, amount, and category using its ID.
- Delete Expense: Remove an expense by specifying its ID.
- List Expenses: Display all recorded expenses with their details.
- Monthly Summary: Provide the total expenses for a specific month.
- Data Persistence: Save expenses as JSON files categorized by month for easy loading on startup.
- Export to CSV: Export expenses to a CSV file for easy sharing and record-keeping.

## Compilation Instructions
Ensure you have the Java Development Kit (JDK) installed on your system.

1. Clone the repository:
   ```bash
   git clone https://github.com/whsad/Expense-Tracker.git
   cd Expense-Tracker
   ```

2. Compile the project:
   ```bash
   javac -encoding UTF-8 Expense.java ExpenseCliApp.java ExpenseManager.java
   ```

## Usage
Run the application with the following commands:

- **Add a new expense**:
  ```bash
  java ExpenseCliApp add --description "Lunch" --amount 20
  ```

- **Delete an expense**:
  ```bash
  java ExpenseCliApp delete --id <expenseId>
  ```

- **List all expenses**:
  ```bash
  java ExpenseCliApp list
  ```

- **Display a summary**:
  - Total expenses:
    ```bash
    java ExpenseCliApp summary
    ```
  - Total expenses for a specific month:
    ```bash
    java ExpenseCliApp summary --month 8
    ```

## Notes
- Expenses are stored in src/JSON_Files/ as individual JSON files categorized by month.
- If the directory or JSON files are missing, the application automatically creates them on startup.
-User credentials are stored in src/resources/users.json.

## Acknowledgments
This project is a solution to a project challenge that emphasizes building essential skills in command-line applications, data persistence, and Java programming.

This is a solution to a project challenge in [roadmap.sh](https://roadmap.sh/projects/expense-tracker).
