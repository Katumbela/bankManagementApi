package com.katumbela.bankManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

import java.util.Date;

import io.jsonwebtoken.Jwt;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "minhaChaveSecreta";
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public String gerarToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extrairEmail(String token) {
        return obterClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        try {
            obterClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims obterClaims(String token) {

        Jwt parse = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(30)
                .build()
                .parse(token);
        Claims body = (Claims) parse.getBody();

        return body;
    }
}
