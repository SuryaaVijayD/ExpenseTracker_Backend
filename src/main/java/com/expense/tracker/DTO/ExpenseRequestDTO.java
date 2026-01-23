package com.expense.tracker.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseRequestDTO {

    private BigDecimal amount;
    private String category;
    private String description;
    private LocalDateTime expenseDate;
}
