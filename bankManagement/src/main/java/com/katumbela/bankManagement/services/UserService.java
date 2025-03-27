package com.katumbela.bankManagement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private TransactionRepository transactionRepository;

    // @Autowired
    // private AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "User not found with id " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return user;

    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User updateUser(User user) {

        User obj = findById(user.getId());

        obj.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(obj);
    }

}