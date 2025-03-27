package com.katumbela.bankManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByTransactionDateDesc(Account account);
}
