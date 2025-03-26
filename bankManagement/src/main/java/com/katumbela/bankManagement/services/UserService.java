package com.katumbela.bankManagement.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "User not found with id " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User createUser(User user) {
        user = this.userRepository.save(user);
        return user;
    }

    public User updateUser(User user) {

        User obj = findById(user.getId());

        obj.setPassword(user.getPassword());

        return this.userRepository.save(obj);
    }

    public void deleteUser (Long id) {

        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with id " + id);
        }

    }
}