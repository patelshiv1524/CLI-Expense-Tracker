package utils;

import model.Expense;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileManager {
    private final Gson gson = new Gson();
    private static final String RESOURCES_DIR = "src/resources/JSON_Files/";

    private String getFilePathForMonth(Date date) {
        // Format the date to get the month name and year (e.g., "2024-October.json")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM");
        return RESOURCES_DIR + formatter.format(date) + ".json";
    }

    public List<Expense> loadExpensesForMonth(Date date) {
        String filePath = getFilePathForMonth(date);
        File file = new File(filePath);
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<Expense>>() {}.getType());
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Return empty list if file not found
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Expense> loadCurrentMonthExpenses() {
        // Get the current date
        Date currentDate = new Date();
        // Use the existing loadExpensesForMonth method with the current date
        return loadExpensesForMonth(currentDate);
    }

    public void saveExpensesForMonth(List<Expense> expenses, Date date) {
        String filePath = getFilePathForMonth(date);
        File file = new File(filePath);

        // Ensure that the parent directory (src/resources) exists
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Created directory: " + parentDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                return;
            }
        }

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all expenses from all monthly files for the summary
    public List<Expense> loadAllExpenses() {
        List<Expense> allExpenses = new ArrayList<>();
        File directory = new File(RESOURCES_DIR);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try (Reader reader = new FileReader(file)) {
                    List<Expense> expenses = gson.fromJson(reader, new TypeToken<List<Expense>>() {}.getType());
                    if (expenses != null) {
                        allExpenses.addAll(expenses);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allExpenses;
    }

    public List<User> loadUsers() {
        String filePath = "src/resources/users.json";
        File file = new File(filePath);
        try (Reader reader = new FileReader(file)) {
            // Check if the file is empty
            if (file.length() == 0) {
                return new ArrayList<>(); // Return an empty list if the file is empty
            }
            return gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
        } catch (FileNotFoundException e) {
            // Return an empty list if the file does not exist
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveUsers(List<User> users) {
        String filePath = "src/resources/users.json";
        File file = new File(filePath);
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
