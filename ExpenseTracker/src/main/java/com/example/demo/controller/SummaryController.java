package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MonthlySummaryDto;
import com.example.demo.service.TransactionService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    private final TransactionService transactionService;

    public SummaryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/monthly")
    public MonthlySummaryDto getMonthlySummary(
            @RequestParam int month,
            @RequestParam int year) {

        return transactionService.getMonthlySummary(month, year);
    }
    
    @GetMapping("/monthly/export")
    public void exportMonthlySummary(
            @RequestParam int month,
            @RequestParam int year,
            HttpServletResponse response) throws IOException {

        // Get summary data
        MonthlySummaryDto summary =
                transactionService.getMonthlySummary(month, year);

        // Set response headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=monthly-summary.csv");

        PrintWriter writer = response.getWriter();

        // Write CSV Header
        writer.println("Monthly Summary Report");
        writer.println("Month," + month);
        writer.println("Year," + year);
        writer.println();

        // Write totals
        writer.println("Total Income," + summary.getTotalIncome());
        writer.println("Total Expense," + summary.getTotalExpense());
        writer.println("Balance," + summary.getBalance());
        writer.println();

        // Category-wise breakdown
        writer.println("Category,Total Expense");

        for (Map.Entry<String, BigDecimal> entry :
                summary.getCategoryWiseExpense().entrySet()) {

            writer.println(entry.getKey() + "," + entry.getValue());
        }

        writer.flush();
        writer.close();
    }

}