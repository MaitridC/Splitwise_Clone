package com.example.splitwise.repository;

import com.example.splitwise.entity.Expense;
import com.example.splitwise.entity.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Long> {
    List<ExpenseShare>  findAllByExpense(Expense expense);
}
