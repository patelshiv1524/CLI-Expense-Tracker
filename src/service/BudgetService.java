package service;

public class BudgetService {
    private double monthlyBudget;

    public void setMonthlyBudget(double amount) {
        this.monthlyBudget = amount;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public boolean isBudgetExceeded(double currentMonthTotal) {
        return currentMonthTotal > monthlyBudget;
    }
}
