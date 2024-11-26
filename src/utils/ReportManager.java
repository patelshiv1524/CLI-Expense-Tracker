package utils;

import model.Expense;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportManager {
    public void exportToCSV(List<Expense> expenses, String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // Ensure the parent directory exists
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Created directory: " + parentDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                return;
            }
        }

        // Write the expenses to the CSV file
        try (FileWriter writer = new FileWriter(file)) {
            writer.append("ID,Description,Amount,Date,Category\n");
            for (Expense expense : expenses) {
                writer.append(expense.getId() + ",")
                        .append(expense.getDescription() + ",")
                        .append(String.valueOf(expense.getAmount()) + ",")
                        .append(expense.getDate() + ",")
                        .append(expense.getCategory() != null ? expense.getCategory().getName() : "N/A")
                        .append("\n");
            }
            System.out.println("Expenses exported successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
