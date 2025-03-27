package com.katumbela.bankManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.katumbela.bankManagement.dtos.LoginDTO;
import com.katumbela.bankManagement.dtos.UserDTO;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.security.JwtUtil;
import com.katumbela.bankManagement.services.UserMapper;
import com.katumbela.bankManagement.services.UserService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDTO userDTO) {
        try {
            // Directly pass the UserDTO to the service
            User createdUser = userService.createUser(userDTO);

            // Create a response with user info but without sensitive data
            Map<String, Object> response = new HashMap<>();
            response.put("id", createdUser.getId());
            response.put("username", createdUser.getUsername());
            response.put("email", createdUser.getEmail());
            response.put("message", "User registered successfully");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Registration failed: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        try {
            Optional<User> userOptional = userService.findByEmail(request.getEmail());

            if (userOptional.isPresent()
                    && passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
                String token = jwtUtil.gerarToken(userOptional.get().getEmail());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("type", "Bearer");

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
