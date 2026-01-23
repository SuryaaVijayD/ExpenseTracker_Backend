package com.expense.tracker.service;

import com.expense.tracker.model.Expense;
import com.expense.tracker.model.PersonData;
import com.expense.tracker.repo.ExpenseRepository;
import com.expense.tracker.repo.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PersonRepository personRepository;

    public Expense addExpense(
            String username,
            BigDecimal amount,
            String category,
            String description,
            LocalDateTime expenseDate
    ) {
        PersonData user = personRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDescription(description);
        expense.setExpenseDate(expenseDate);
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    public List<Expense> getMyExpenses(String username) {
        PersonData user = personRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return expenseRepository.findByUser(user);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }


    @Transactional
    public void cleanupOldExpenses() {
        LocalDateTime cutoff =
                LocalDateTime.now()
                        .minusMonths(2)
                        .withDayOfMonth(1)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0);

        expenseRepository.deleteOlderThan(cutoff);
    }

    public List<Expense> getExpensesByMonth(
        String username,
        int month,
        int year
    ) {
        PersonData user = personRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDateTime startDate =
                LocalDateTime.of(year, month, 1, 0, 0, 0);

        LocalDateTime endDate =
                startDate
                        .plusMonths(1)
                        .minusSeconds(1);

        return expenseRepository
                .findByUserAndExpenseDateBetween(
                        user,
                        startDate,
                        endDate
                );
    }

}
