package com.katumbela.bankManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.katumbela.bankManagement.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
