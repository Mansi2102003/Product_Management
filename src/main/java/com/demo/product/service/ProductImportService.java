package com.demo.product.service;

import com.demo.product.dao.ProductDAO;
import com.demo.product.model.Product;
import com.demo.product.util.ExcelUtil;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

//Contains business logic - validate inputs
//Calls DAO methods(to store data or retrieve data from database)
//Calls ExcelUtil methods(to write and read Excel)
public class ProductImportService {

	private ProductDAO dao = new ProductDAO();
	
	// Constructor injection for testing
    public ProductImportService(ProductDAO dao) {
        this.dao = dao;
    }

    // Default constructor for normal use
    public ProductImportService() {
        this.dao = new ProductDAO();
    }

	// Business logic for import products
	public void importProducts(String filePath) {

		// Calls ExcelUtil's method readExcel() which returns the list of products data
		List<Product> products = ExcelUtil.readExcel(filePath);

		// Contains products list which failed to import to database
		List<Product> failed = new ArrayList<>();

		// Variables that contains the count of inserted recorsds, skipped records &
		// failed records
		int inserted = 0, skipped = 0, failedCount = 0;

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String logPath = "./logs/Product_ErrorLog_" + timestamp + ".txt";

		/*
		 * Java statement that creates a BufferedWriter for efficient file writing.
		 * FileWriter object: This object directly connects to the file specified by
		 * logPath.By default, it will overwrite the file's contents if the file already
		 * exists.
		 * 
		 */
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logPath))) {

			for (Product p : products) {

				// Validation rules
				if (p.getCategory() == null || p.getCategory().isBlank()) {
					writer.write("NULL value which is Invalid Category for Product: " + p.getProductCode());
					writer.newLine();
					failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getUom() == null || p.getUom().isBlank()) {
					writer.write("NULL value which is Invalid UOM for Product: " + p.getProductCode());
					writer.newLine();
					failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getProductCode() == null || p.getProductCode().isBlank()) {
					writer.write("Missing Product Code");
					writer.newLine();
					failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getProductName() == null || p.getProductName().isBlank()) {
					writer.write("Missing Product Name for Product: " + p.getProductCode());
					writer.newLine();
					failed.add(p);
					failedCount++;
					continue;
				}

				// Duplicate check
				if (dao.existsByCode(p.getProductCode())) {
					writer.write("Skipped duplicate Product: " + p.getProductCode());
					writer.newLine();
					skipped++;
					continue;
				}

				// Try insert
				int res = dao.insert(p);
				if (res > 0)
					inserted++;
				else {
					writer.write("DB Insert failed for Product: " + p.getProductCode());
					writer.newLine();
					failed.add(p);//
					failedCount++;
				}
			}

			writer.write("\nSummary → Inserted: " + inserted + " | Skipped: " + skipped + " | Failed: " + failedCount);
			writer.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Export failed products to Excel
		if (!failed.isEmpty()) {
			String errorExcel = "./output/Product_Error_" + timestamp + ".xlsx";
			ExcelUtil.writeErrorExcel(failed, errorExcel);
			System.out.println("Exported failed rows to: " + errorExcel);
			System.out.println("Error details logged in: " + logPath);
		}
		System.out.println("Import Complete!");
		System.out.printf("\nInserted: %d \n Skipped: %d \n Failed: %d%n\n", inserted, skipped, failedCount);
	}
}
