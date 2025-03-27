package com.katumbela.bankManagement.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.Transaction;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.repositories.AccountRepository;
import com.katumbela.bankManagement.repositories.TransactionRepository;
import com.katumbela.bankManagement.repositories.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    public List<Transaction> getAccountTransactions(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
        return transactionRepository.findByAccountOrderByTransactionDateDesc(account);
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        transaction.setCreatedAt(new Date());
        transaction.setUpdatedAt(new Date());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transferByAccountNumber(String fromAccountNumber, String toAccountNumber, BigDecimal amount,
            String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found: " + fromAccountNumber));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found: " + toAccountNumber));

        return transferBetweenAccounts(fromAccount, toAccount, amount, description);
    }

    @Transactional
    public Transaction transferByEmail(String fromAccountNumber, String toEmail, BigDecimal amount,
            String description) {
        User toUser = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + toEmail));

        Account toAccount = toUser.getAccount();
        if (toAccount == null) {
            throw new RuntimeException("User doesn't have an account: " + toEmail);
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found: " + fromAccountNumber));

        return transferBetweenAccounts(fromAccount, toAccount, amount, description);
    }

    @Transactional
    public Transaction transferByUsername(String fromAccountNumber, String toUsername, BigDecimal amount,
            String description) {
        User toUser = userRepository.findByUsername(toUsername)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + toUsername));

        Account toAccount = toUser.getAccount();
        if (toAccount == null) {
            throw new RuntimeException("User doesn't have an account: " + toUsername);
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found: " + fromAccountNumber));

        return transferBetweenAccounts(fromAccount, toAccount, amount, description);
    }

    private Transaction transferBetweenAccounts(Account fromAccount, Account toAccount, BigDecimal amount,
            String description) {
        // Deduct from source account
        accountService.updateBalance(fromAccount, amount.negate());

        // Add to destination account
        accountService.updateBalance(toAccount, amount);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setAmount(amount.negate());
        transaction.setTransactionType("TRANSFER");
        transaction.setDescription(description);
        transaction.setTransactionDate(new Date());
        transaction.setCreatedAt(new Date());
        transaction.setUpdatedAt(new Date());
        transaction.setFromAccount(fromAccount.getAccountNumber());
        transaction.setToAccount(toAccount.getAccountNumber());

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Create receipt transaction for destination account
        Transaction receiptTransaction = new Transaction();
        receiptTransaction.setAccount(toAccount);
        receiptTransaction.setAmount(amount);
        receiptTransaction.setTransactionType("RECEIPT");
        receiptTransaction.setDescription("Receipt: " + description);
        receiptTransaction.setTransactionDate(new Date());
        receiptTransaction.setCreatedAt(new Date());
        receiptTransaction.setUpdatedAt(new Date());
        receiptTransaction.setFromAccount(fromAccount.getAccountNumber());
        receiptTransaction.setToAccount(toAccount.getAccountNumber());

        transactionRepository.save(receiptTransaction);

        return savedTransaction;
    }
}
