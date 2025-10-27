package com.demo.product.report;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.demo.product.util.DBConnection;

import net.sf.jasperreports.engine.*;

public class ProductReportGenerator {

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Get DB connection from your existing utility class
            connection = DBConnection.getConnection();

            // Load JRXML file from resources
            InputStream reportStream = ProductReportGenerator.class
                    .getResourceAsStream("/reports/product_report.jrxml");

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Fill report with data from PostgreSQL
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

            // Save as PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "Product_Report.pdf");
            System.out.println("Product_Report.pdf generated successfully!");


        } catch (JRException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

