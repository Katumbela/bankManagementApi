package com.katumbela.bankManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.katumbela.bankManagement.services.UserService;

import java.util.Optional;

import com.katumbela.bankManagement.dtos.LoginDTO;
import com.katumbela.bankManagement.models.User;
import com.katumbela.bankManagement.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        Optional<User> usuario = usuarioService.findByEmail(request.getEmail());

        if (usuario.isPresent() && passwordEncoder.matches(request.getPassword(), usuario.get().getPassword())) {
            String token = jwtUtil.gerarToken(usuario.get().getEmail());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
