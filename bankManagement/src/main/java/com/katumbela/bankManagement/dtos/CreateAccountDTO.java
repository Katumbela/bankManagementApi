package com.katumbela.bankManagement.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountDTO {
    private Long userId;
    private String accountType;
}
