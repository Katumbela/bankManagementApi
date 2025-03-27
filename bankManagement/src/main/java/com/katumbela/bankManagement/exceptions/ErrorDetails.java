package com.katumbela.bankManagement.exceptions;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
