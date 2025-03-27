package com.katumbela.bankManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.Transaction;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.services.AccountService;
import com.katumbela.bankManagement.services.TransactionService;
import com.katumbela.bankManagement.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/open-banking/api")
public class OpenBankingController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccount(
            @PathVariable Long accountId,
            @AuthenticationPrincipal BearerTokenAuthentication authentication) {

        return accountService.findById(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(
            @PathVariable Long accountId,
            @AuthenticationPrincipal BearerTokenAuthentication authentication) {

        List<Transaction> transactions = transactionService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @AuthenticationPrincipal BearerTokenAuthentication authentication) {

        String email = authentication.getName();
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
