package br.com.fiap.finance_walk_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.finance_walk_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    // List<Transaction> findByDescriptionContainingIgnoringCase(String
    // description); //Query Method

    // List<Transaction> findByDescriptionContainingIgnoringCaseAndDate(String
    // description, LocalDate date);

    // List<Transaction> findByDate(LocalDate date);

}
