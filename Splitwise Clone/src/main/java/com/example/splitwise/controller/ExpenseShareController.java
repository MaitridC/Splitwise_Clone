package com.example.splitwise.controller;

import com.example.splitwise.dto.ExpenseShareDTO;
import com.example.splitwise.service.ExpenseShareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-shares")
public class ExpenseShareController {

    private final ExpenseShareService expenseShareService;

    public ExpenseShareController(ExpenseShareService expenseShareService) {
        this.expenseShareService = expenseShareService;
    }

    @GetMapping
    public List<ExpenseShareDTO> getAllShares() {
        return expenseShareService.getAllShares();
    }

    @GetMapping("/{id}")
    public ExpenseShareDTO getShareById(@PathVariable Long id) {
        return expenseShareService.getShareById(id);
    }
}
