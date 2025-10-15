package com.demo.product.service;

import com.demo.product.dao.ProductDAO;
import com.demo.product.model.Product;
import com.demo.product.util.ExcelUtil;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ProductImportService {

	private final ProductDAO dao = new ProductDAO();

	public void importProducts(String filePath) {
		List<Product> products = ExcelUtil.readExcel(filePath);
		//List<Product> failed = new ArrayList<>();
		int inserted = 0, skipped = 0, failedCount = 0;

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String logPath = "./logs/Product_ErrorLog_" + timestamp + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logPath))) {

			for (Product p : products) {

				// Validation rules
				if (p.getCategory() == null || p.getCategory().isBlank()) {
					writer.write("NULL value which is Invalid Category for Product: " + p.getProductCode());
					writer.newLine();
					//failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getUom() == null || p.getUom().isBlank()) {
					writer.write("NULL value which is Invalid UOM for Product: " + p.getProductCode());
					writer.newLine();
					//failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getProductCode() == null || p.getProductCode().isBlank()) {
					writer.write("Missing Product Code");
					writer.newLine();
					//failed.add(p);
					failedCount++;
					continue;
				}

				if (p.getProductName() == null || p.getProductName().isBlank()) {
					writer.write("Missing Product Name for Product: " + p.getProductCode());
					writer.newLine();
					//failed.add(p);
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
					//failed.add(p);
					failedCount++;
				}
			}

			writer.write("\nSummary → Inserted: " + inserted + " | Skipped: " + skipped + " | Failed: " + failedCount);
			writer.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		 

		System.out.println(" Import Complete!");
		System.out.printf("\nInserted: %d \n Skipped: %d \n Failed: %d%n\n", inserted, skipped, failedCount);
	}
}
