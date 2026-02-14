package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MonthlySummaryDto;
import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionType;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction addTransaction(TransactionDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
;

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) {

        if (start.isAfter(end)) {
        	throw new BadRequestException("Start date cannot be after end date");
        }

        return transactionRepository.findByDateBetween(start, end);
    }
    
    public MonthlySummaryDto getMonthlySummary(int month, int year) {

        BigDecimal income = transactionRepository
                .getMonthlyTotal(TransactionType.INCOME, month, year);

        BigDecimal expense = transactionRepository
                .getMonthlyTotal(TransactionType.EXPENSE, month, year);

        // Handle null values (if no records found)
        if (income == null) income = BigDecimal.ZERO;
        if (expense == null) expense = BigDecimal.ZERO;

        BigDecimal balance = income.subtract(expense);

        // Category-wise expense
        List<Object[]> results =
                transactionRepository.getCategoryWiseExpense(month, year);

        Map<String, BigDecimal> categoryMap = new HashMap<>();

        for (Object[] row : results) {
            String categoryName = (String) row[0];
            BigDecimal total = (BigDecimal) row[1];
            categoryMap.put(categoryName, total);
        }

        return new MonthlySummaryDto(income, expense, balance, categoryMap);
    }
}