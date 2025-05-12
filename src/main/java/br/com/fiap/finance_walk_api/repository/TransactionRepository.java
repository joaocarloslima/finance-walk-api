package br.com.fiap.finance_walk_api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.finance_walk_api.model.Transaction;
import br.com.fiap.finance_walk_api.model.TransactionType;
import br.com.fiap.finance_walk_api.model.User;
import br.com.fiap.finance_walk_api.repository.CategoryExpenseData;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    // List<Transaction> findByDescriptionContainingIgnoringCase(String
    // description); //Query Method

    // List<Transaction> findByDescriptionContainingIgnoringCaseAndDate(String
    // description, LocalDate date);

    // List<Transaction> findByDate(LocalDate date);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t
                WHERE t.user = :user AND t.type = 'INCOME'
                AND FUNCTION('MONTH', t.date) = FUNCTION('MONTH', CURRENT_DATE)
                AND FUNCTION('YEAR', t.date) = FUNCTION('YEAR', CURRENT_DATE)
            """)
    BigDecimal sumCurrentMonthIncomeByUser(User user);

    @Query("""
        SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t
            WHERE t.user = :user AND t.type = 'EXPENSE'
            AND FUNCTION('MONTH', t.date) = FUNCTION('MONTH', CURRENT_DATE)
            AND FUNCTION('YEAR', t.date) = FUNCTION('YEAR', CURRENT_DATE)
        """)
BigDecimal sumCurrentMonthExpenseByUser(User user);

@Query("SELECT t FROM Transaction t " +
 "WHERE t.user = :user AND t.type = 'EXPENSE' " +
 "AND FUNCTION('MONTH', t.date) = FUNCTION('MONTH', CURRENT_DATE) " +
 "AND FUNCTION('YEAR', t.date) = FUNCTION('YEAR', CURRENT_DATE) " +
 "ORDER BY t.amount DESC LIMIT 1")
Optional<Transaction> findTopExpenseByUserCurrentMonth(@Param("user") User user);

@Query("""
    SELECT new br.com.fiap.finance_walk_api.repository.CategoryExpenseData(c.name, SUM(t.amount))
    FROM Transaction t
    JOIN t.category c
    WHERE t.user = :user
    AND t.type = 'EXPENSE'
    AND FUNCTION('MONTH', t.date) = FUNCTION('MONTH', CURRENT_DATE)
    AND FUNCTION('YEAR', t.date) = FUNCTION('YEAR', CURRENT_DATE)
    GROUP BY c.name
    ORDER BY SUM(t.amount) DESC
""")
List<CategoryExpenseData> findCurrentMonthExpensesByCategory(@Param("user") User user);

List<Transaction> findByTypeAndDateBetween(TransactionType type, LocalDate startDate, LocalDate endDate);

List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);


}
