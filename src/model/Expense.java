package model;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private String date;
    private Category category;

    public Expense(int id, String description, double amount, String date, Category category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category= this.category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Date: " + date + " | Description: " + description + " | Amount: $" + amount;
    }

    public Category getCategory() {
        return category;
    }
}
