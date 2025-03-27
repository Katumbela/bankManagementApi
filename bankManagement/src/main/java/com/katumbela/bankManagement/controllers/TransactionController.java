package com.katumbela.bankManagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.katumbela.bankManagement.dtos.TransferDTO;
import com.katumbela.bankManagement.models.Transaction;
import com.katumbela.bankManagement.services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer/account")
    public ResponseEntity<Transaction> transferByAccountNumber(@RequestBody TransferDTO transferDTO) {
        Transaction transaction = transactionService.transferByAccountNumber(
                transferDTO.getFromAccountNumber(),
                transferDTO.getToAccountNumber(),
                transferDTO.getAmount(),
                transferDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @PostMapping("/transfer/email")
    public ResponseEntity<Transaction> transferByEmail(@RequestBody TransferDTO transferDTO) {
        Transaction transaction = transactionService.transferByEmail(
                transferDTO.getFromAccountNumber(),
                transferDTO.getToEmail(),
                transferDTO.getAmount(),
                transferDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @PostMapping("/transfer/username")
    public ResponseEntity<Transaction> transferByUsername(@RequestBody TransferDTO transferDTO) {
        Transaction transaction = transactionService.transferByUsername(
                transferDTO.getFromAccountNumber(),
                transferDTO.getToUsername(),
                transferDTO.getAmount(),
                transferDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
