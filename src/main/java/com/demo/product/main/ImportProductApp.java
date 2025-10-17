package com.demo.product.main;

import com.demo.product.service.ProductImportService;

//Entry point for Execution of program to Import data to database
public class ImportProductApp {
	public static void main(String[] args) {

		// Path of input Excel file
		String filePath = "/home/mansi/eclipse-workspace/Product_Master/src/main/resources/Product_Master_NewData.xlsx";

		// Calls Service class method which contains the logic to import products to
		// database
		new ProductImportService().importProducts(filePath);

	}
}
