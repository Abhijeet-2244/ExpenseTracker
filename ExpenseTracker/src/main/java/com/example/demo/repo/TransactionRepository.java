package com.example.demo.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findByDateBetween(LocalDate start, LocalDate end);
	
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND MONTH(t.date)=:month AND YEAR(t.date)=:year")
    BigDecimal getMonthlyTotal(@Param("type") TransactionType type,
                               @Param("month") int month,
                               @Param("year") int year);
	
	@Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t " +
	           "WHERE t.type='EXPENSE' AND MONTH(t.date)=:month AND YEAR(t.date)=:year " +
	           "GROUP BY t.category.name")
	    List<Object[]> getCategoryWiseExpense(@Param("month") int month,
	                                          @Param("year") int year);
	}
	
	

