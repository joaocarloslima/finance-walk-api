package br.com.fiap.finance_walk_api.repository;

import java.math.BigDecimal;


public record CategoryExpenseData(String category, BigDecimal total) {
}
