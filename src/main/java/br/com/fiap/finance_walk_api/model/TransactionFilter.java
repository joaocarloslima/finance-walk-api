package br.com.fiap.finance_walk_api.model;

import java.time.LocalDate;

public record TransactionFilter(String description, LocalDate startDate, LocalDate endDate) {
}
