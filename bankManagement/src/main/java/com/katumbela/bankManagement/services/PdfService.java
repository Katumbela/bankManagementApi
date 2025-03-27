package com.katumbela.bankManagement.services;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.katumbela.bankManagement.models.Account;
import com.katumbela.bankManagement.models.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

    @Autowired
    private TransactionService transactionService;

    public byte[] generateAccountStatement(Account account, String startDate, String endDate) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            addMetaData(document);
            addTitlePage(document, account);
            addContent(document, account);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }

        return baos.toByteArray();
    }

    private void addMetaData(Document document) {
        document.addTitle("Account Statement");
        document.addSubject("Account Statement");
        document.addKeywords("Bank, Account, Statement");
        document.addAuthor("Bank Management System");
        document.addCreator("Bank Management System");
    }

    private void addTitlePage(Document document, Account account) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Account Statement", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" "));

        Font accountFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Paragraph accountInfo = new Paragraph("Account Number: " + account.getAccountNumber(), accountFont);
        accountInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(accountInfo);

        Paragraph accountType = new Paragraph("Account Type: " + account.getAccountType(), accountFont);
        accountType.setAlignment(Element.ALIGN_LEFT);
        document.add(accountType);

        Paragraph accountBalance = new Paragraph("Account Balance: " + account.getAccountBalance(), accountFont);
        accountBalance.setAlignment(Element.ALIGN_LEFT);
        document.add(accountBalance);

        if (account.getUser() != null) {
            Paragraph accountOwner = new Paragraph("Account Owner: " + account.getUser().getUsername(), accountFont);
            accountOwner.setAlignment(Element.ALIGN_LEFT);
            document.add(accountOwner);
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
    }

    private void addContent(Document document, Account account) throws DocumentException {
        List<Transaction> transactions = transactionService.getAccountTransactions(account.getId());

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Set table headers
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        addTableHeader(table, headerFont, "Date", "Type", "Description", "Amount", "Balance");

        // Set table content
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Start with current balance and work backwards
        double runningBalance = Double.parseDouble(account.getAccountBalance());

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount().doubleValue();
            // For display purposes, we add the amount back since we want to show history
            if ("TRANSFER".equals(transaction.getTransactionType())) {
                runningBalance -= amount;
            } else if ("RECEIPT".equals(transaction.getTransactionType())) {
                runningBalance -= amount;
            }

            addTableRow(table, contentFont,
                    dateFormat.format(transaction.getTransactionDate()),
                    transaction.getTransactionType(),
                    transaction.getDescription(),
                    transaction.getAmount().toString(),
                    String.valueOf(runningBalance));
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table, Font font, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(220, 220, 220));
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private void addTableRow(PdfPTable table, Font font, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
}
