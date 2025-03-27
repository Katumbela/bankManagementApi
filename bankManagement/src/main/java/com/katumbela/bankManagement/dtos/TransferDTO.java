package com.katumbela.bankManagement.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String toEmail;
    private String toUsername;
    private BigDecimal amount;
    private String description;
}
