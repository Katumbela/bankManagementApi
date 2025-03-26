package com.katumbela.bankManagement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.Transaction;
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

    @Transactional
    public Transaction create(Transaction obj) {
        // Optional<Account> account =
        // this.accountRepository.findById(obj.getAccount().getId());
        // obj.setId(null);
        // obj.setAccount(account ? account : null);
        obj = this.transactionRepository.save(obj);
        return obj;
    }

}
