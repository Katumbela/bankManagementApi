package com.katumbela.bankManagement.util;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class AccountNumberUtil {
    
    private static final Random RANDOM = new Random();
    
    /**
     * Generates a random 10-digit account number
     */
    public String generateAccountNumber() {
        StringBuilder sb = new StringBuilder();
        // First digit should not be 0
        sb.append(1 + RANDOM.nextInt(9));
        
        // Add 9 more random digits
        for (int i = 0; i < 9; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        
        return sb.toString();
    }
}
