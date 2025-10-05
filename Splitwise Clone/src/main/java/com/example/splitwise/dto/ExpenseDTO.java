package com.example.splitwise.dto;

import java.time.LocalDate;
import java.util.List;

public class ExpenseDTO {

    private Long id;
    private double amount;
    private String description;
    private LocalDate date;
    private Long paidByUserId;
    private Long groupId;
    private List<Long> sharedWithUserIds;

    public ExpenseDTO(Long id, double amount, String description, LocalDate date,
                      Long paidByUserId, Long groupId, List<Long> sharedWithUserIds) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.paidByUserId = paidByUserId;
        this.groupId = groupId;
        this.sharedWithUserIds = sharedWithUserIds;
    }

    public ExpenseDTO() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getPaidByUserId() { return paidByUserId; }
    public void setPaidByUserId(Long paidByUserId) { this.paidByUserId = paidByUserId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public List<Long> getSharedWithUserIds() { return sharedWithUserIds; }
    public void setSharedWithUserIds(List<Long> sharedWithUserIds) { this.sharedWithUserIds = sharedWithUserIds; }
}
