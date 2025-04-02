package com.katumbela.bankManagement.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katumbela.bankManagement.exceptions.ResourceNotFoundException;
import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.repositories.AccountRepository;
import com.katumbela.bankManagement.repositories.UserRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountNumberUtil accountNumberUtil;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public Account createAccount(Long userId, String accountType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Generate unique account number
        String accountNumber = generateAccountNumber();

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setAccountStatus("ACTIVE");
        account.setAccountBalance("0.00");
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        // Update user with account reference
        user.setAccount(savedAccount);
        userRepository.save(user);

        return savedAccount;
    }

    @Transactional
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public Account createDefaultAccount(com.katumbela.bankManagement.models.User user) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType("Savings"); // Default type
        account.setAccountStatus("Active");
        account.setAccountBalance(BigDecimal.ZERO);
        account.setUser(user);

        return accountRepository.save(account);
    }

    public String generateAccountNumber() {
        return accountNumberUtil.generateAccountNumber();
    }

    @Transactional
    public void updateBalance(Account account, BigDecimal amount) {
        BigDecimal currentBalance = new BigDecimal(account.getAccountBalance());
        BigDecimal newBalance = currentBalance.add(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setAccountBalance(newBalance.toString());
        accountRepository.save(account);
    }
}
