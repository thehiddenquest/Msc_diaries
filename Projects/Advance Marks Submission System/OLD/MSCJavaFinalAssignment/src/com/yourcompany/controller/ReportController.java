// ReportController.java
package com.yourcompany.controller;

import com.yourcompany.model.Report;
import com.yourcompany.service.DatabaseService;
import com.yourcompany.service.EmailService;
import com.yourcompany.service.ExcelService;
import com.yourcompany.view.ReportView;

public class ReportController {
    private ExcelService excelService;
    private DatabaseService databaseService;
    private EmailService emailService;
    private ReportView reportView;

    public ReportController(ExcelService excelService, DatabaseService databaseService, EmailService emailService, ReportView reportView) {
        this.excelService = excelService;
        this.databaseService = databaseService;
        this.emailService = emailService;
        this.reportView = reportView;
    }

    public void uploadMarks(String filePath) {
        excelService.parseExcelAndPersist(filePath);
    }

    public void generateReport() {
        Report report = databaseService.generateReport();
        reportView.displayReport(report);
    }

    public void emailReport(String recipient) {
        Report report = databaseService.generateReport();
        emailService.sendReportByEmail(recipient, report);
    }
}
