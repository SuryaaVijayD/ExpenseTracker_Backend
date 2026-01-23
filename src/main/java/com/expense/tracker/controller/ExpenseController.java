package com.expense.tracker.controller;

import com.expense.tracker.DTO.ExpenseRequestDTO;
import com.expense.tracker.model.Expense;
import com.expense.tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense addExpense(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ExpenseRequestDTO request
    ) {
        return expenseService.addExpense(
                userDetails.getUsername(),
                request.getAmount(),
                request.getCategory(),
                request.getDescription(),
                request.getExpenseDate()
        );
    }

    @GetMapping("/getall")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/me")
    public List<Expense> getMyExpenses(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return expenseService.getMyExpenses(userDetails.getUsername());
    }

    @GetMapping("/month")
    public List<Expense> getExpensesByMonth(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam int month,
            @RequestParam int year
    ) {
        return expenseService.getExpensesByMonth(
                userDetails.getUsername(),
                month,
                year
        );
    }

}
