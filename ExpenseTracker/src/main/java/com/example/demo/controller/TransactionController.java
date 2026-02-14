package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TransactionDto;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService=transactionService;
	}
	
	 @PostMapping
	    public ResponseEntity<Transaction> addTransaction(@Valid @RequestBody TransactionDto dto) {
	        return ResponseEntity.ok(transactionService.addTransaction(dto));
	    }
	 
	 
	 @GetMapping
	 public List<Transaction> getByDateRange(
	         @RequestParam
	         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	         LocalDate start,

	         @RequestParam
	         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	         LocalDate end) {

	     return transactionService.getTransactionsByDateRange(start, end);
	 }
	
}
