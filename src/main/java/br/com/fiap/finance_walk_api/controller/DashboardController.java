package br.com.fiap.finance_walk_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.finance_walk_api.model.User;
import br.com.fiap.finance_walk_api.repository.CategoryExpenseData;
import br.com.fiap.finance_walk_api.repository.TransactionRepository;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    record DashboardData(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal topExpense, String topDescription) {}

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public DashboardData getTotalIncome(@AuthenticationPrincipal User user) {
        var totalIncome = transactionRepository.sumCurrentMonthIncomeByUser(user);
        var totalExpense = transactionRepository.sumCurrentMonthExpenseByUser(user);
        var topExpense = transactionRepository.findTopExpenseByUserCurrentMonth(user);
        var topDescription = topExpense
                                .map(t -> t.getDescription())
                                .orElse("Sem despesas");
        BigDecimal topExpenseValue = topExpense
                                .map(t -> t.getAmount())
                                .orElse(BigDecimal.ZERO);
        
        return new DashboardData(totalIncome, totalExpense, topExpenseValue, topDescription);
    }

    @GetMapping("/graficos")
    public List<CategoryExpenseData> getCategoryExpense(@AuthenticationPrincipal User user) {
        return transactionRepository.findCurrentMonthExpensesByCategory(user);
    }
    
}