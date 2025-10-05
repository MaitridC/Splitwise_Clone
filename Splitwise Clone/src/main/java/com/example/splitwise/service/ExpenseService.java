package com.example.splitwise.service;

import com.example.splitwise.dto.ExpenseDTO;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseShare;
import com.example.splitwise.entity.Group;
import com.example.splitwise.entity.User;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.ExpenseShareRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseShareRepository expenseShareRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository,
                          GroupRepository groupRepository,
                          ExpenseShareRepository expenseShareRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseShareRepository = expenseShareRepository;
    }

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return toDTO(expense);
    }

    public ExpenseDTO createExpense(ExpenseDTO dto) {
        User paidBy = userRepository.findById(dto.getPaidByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Group group = null;
        if (dto.getGroupId() != null) {
            group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
        }

        Expense expense = new Expense();
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());
        expense.setPaidBy(paidBy);
        expense.setGroup(group);

        // Populate sharedWith users
        if (dto.getSharedWithUserIds() != null && !dto.getSharedWithUserIds().isEmpty()) {
            List<User> sharedUsers = dto.getSharedWithUserIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User not found")))
                    .collect(Collectors.toList());
            expense.setSharedWith(sharedUsers);
        }

        Expense savedExpense = expenseRepository.save(expense);

        // Optional: also auto-create ExpenseShares
        if (expense.getSharedWith() != null && !expense.getSharedWith().isEmpty()) {
            double shareAmount = expense.getAmount() / expense.getSharedWith().size();
            for (User user : expense.getSharedWith()) {
                ExpenseShare share = new ExpenseShare();
                share.setExpense(savedExpense);
                share.setUser(user);
                share.setAmount(shareAmount);
                share.setSettled(false);
                expenseShareRepository.save(share);
            }
        }

        return toDTO(savedExpense);
    }

    public ExpenseDTO updateExpense(Long id, ExpenseDTO dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (dto.getPaidByUserId() != null) {
            User paidBy = userRepository.findById(dto.getPaidByUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            expense.setPaidBy(paidBy);
        }

        if (dto.getGroupId() != null) {
            Group group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            expense.setGroup(group);
        }

        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());

        return toDTO(expenseRepository.save(expense));
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDTO toDTO(Expense expense) {
        List<Long> sharedUserIds = expense.getSharedWith().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        Long groupId = expense.getGroup() != null ? expense.getGroup().getGroupId() : null;

        return new ExpenseDTO(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getDate(),
                expense.getPaidBy().getId(),
                groupId,
                sharedUserIds
        );
    }
}
