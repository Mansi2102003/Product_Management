package com.demo.product.main;

import com.demo.product.service.ProductExportService;

//Entry point for Execution of program to Export data from database
public class ExportProductApp {
	public static void main(String[] args) {

		// Calls Service class method which contains the logic of export products from
		// database
		new ProductExportService().exportProducts();
	}
}
