package com.katumbela.bankManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.services.AccountService;
import com.katumbela.bankManagement.services.PdfService;

@RestController
@RequestMapping("/statements")
public class StatementController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<byte[]> getAccountStatement(
            @PathVariable Long accountId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        Account account = accountService.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        byte[] pdfContent = pdfService.generateAccountStatement(account, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "account-statement.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
