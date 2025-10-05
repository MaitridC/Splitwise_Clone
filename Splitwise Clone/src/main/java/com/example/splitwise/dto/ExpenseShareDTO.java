package com.example.splitwise.dto;

public class ExpenseShareDTO {

    private Long id;
    private Long expenseId;
    private Long userId;
    private double amount;
    private boolean settled;

    public ExpenseShareDTO(Long id, Long expenseId, Long userId, double amount, boolean settled) {
        this.id = id;
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.settled = settled;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getExpenseId() { return expenseId; }
    public void setExpenseId(Long expenseId) { this.expenseId = expenseId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public boolean isSettled() { return settled; }
    public void setSettled(boolean settled) { this.settled = settled; }
}
