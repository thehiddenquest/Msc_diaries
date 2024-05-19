// ReportView.java
package com.yourcompany.view;

import com.yourcompany.model.Report;

public class ReportView {
    public void displayReport(Report report) {
        System.out.println("Report:");
        System.out.println(report.getData());
    }
}
