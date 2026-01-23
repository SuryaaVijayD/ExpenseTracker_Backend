package com.expense.tracker.scheduler;

import com.expense.tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCleanupScheduler {

    @Autowired
    private ExpenseService expenseService;

    // Runs at 00:00 on 1st of every month
    @Scheduled(cron = "0 0 0 1 * ?")
    public void cleanOldExpenses() {
        expenseService.cleanupOldExpenses();
    }
}
