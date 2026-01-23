package com.expense.tracker.repo;

import com.expense.tracker.model.Expense;
import com.expense.tracker.model.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(PersonData user);

    List<Expense> findByUserAndExpenseDateBetween(
            PersonData user,
            LocalDateTime start,
            LocalDateTime end
    );

    @Modifying
    @Query("DELETE FROM Expense e WHERE e.expenseDate < :cutoffDate")
    void deleteOlderThan(LocalDateTime cutoffDate);
}
