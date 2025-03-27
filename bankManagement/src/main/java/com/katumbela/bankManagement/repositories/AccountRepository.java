package com.katumbela.bankManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.katumbela.bankManagement.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
