package com.example.demo.dto;


import java.math.BigDecimal;
import java.util.Map;

public class MonthlySummaryDto {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private Map<String, BigDecimal> categoryWiseExpense;

    public MonthlySummaryDto() {
    }

    public MonthlySummaryDto(BigDecimal totalIncome,
                             BigDecimal totalExpense,
                             BigDecimal balance,
                             Map<String, BigDecimal> categoryWiseExpense) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.categoryWiseExpense = categoryWiseExpense;
    }

    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }

    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Map<String, BigDecimal> getCategoryWiseExpense() { return categoryWiseExpense; }
    public void setCategoryWiseExpense(Map<String, BigDecimal> categoryWiseExpense) {
        this.categoryWiseExpense = categoryWiseExpense;
    }
}
