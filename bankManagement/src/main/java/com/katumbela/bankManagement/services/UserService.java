package com.katumbela.bankManagement.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.katumbela.bankManagement.dtos.UserDTO;
import com.katumbela.bankManagement.exceptions.ResourceNotFoundException;
import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.repositories.AccountRepository;
import com.katumbela.bankManagement.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Create account first
        Account account = new Account();
        account.setAccountNumber(accountService.generateAccountNumber());
        account.setAccountType("Savings"); // Default type
        account.setAccountStatus("Active");
        account.setAccountBalance(BigDecimal.ZERO);
        
        // Save the account
        account = accountRepository.save(account);
        
        // Set account to user
        user.setAccount(account);
        
        // Save user with account
        user = userRepository.save(user);
        
        // Update account with user reference
        account.setUser(user);
        accountRepository.save(account);
        
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User updateUser(User user) {
        User existingUser = findById(user.getId());

        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPhone() != null) {
            existingUser.setPhone(user.getPhone());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public UserDTO getUserDTO(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }
}