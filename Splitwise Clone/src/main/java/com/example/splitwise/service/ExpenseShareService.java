package com.example.splitwise.service;

import com.example.splitwise.dto.ExpenseShareDTO;
import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseShare;
import com.example.splitwise.repository.ExpenseShareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseShareService {

    private final ExpenseShareRepository expenseShareRepository;

    public ExpenseShareService(ExpenseShareRepository expenseShareRepository) {
        this.expenseShareRepository = expenseShareRepository;
    }

    public List<ExpenseShareDTO> getAllShares() {
        return expenseShareRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExpenseShareDTO getShareById(Long id) {
        ExpenseShare share = expenseShareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ExpenseShare not found"));
        return toDTO(share);
    }

    public List<ExpenseShareDTO> getSharesByExpense(Expense expense) {
        return expenseShareRepository.findAllByExpense(expense)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ExpenseShareDTO toDTO(ExpenseShare share) {
        return new ExpenseShareDTO(
                share.getId(),
                share.getExpense().getId(),
                share.getUser().getId(),
                share.getAmount(),
                share.isSettled()
        );
    }
}
