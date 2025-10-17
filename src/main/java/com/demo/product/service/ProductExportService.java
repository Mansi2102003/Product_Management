package com.demo.product.service;

import com.demo.product.dao.ProductDAO;
import com.demo.product.model.Product;
import com.demo.product.util.ExcelUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductExportService {
	private final ProductDAO dao = new ProductDAO();

	// Business Logic to export Products data from database and import into Excel
	public void exportProducts() {

		/*
		 * Calls DAO's findAll method which contains the logic to retrive products from
		 * database and returns the list of products
		 */
		List<Product> list = dao.findAll();

		// Creates a file with given file path
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String filePath = "./output/Product_Export_" + timestamp + ".xlsx";

		// Calls ExcelUtil's method writeExcel() to write Excel
		ExcelUtil.writeExcel(list, filePath);
		System.out.println("Export successful! File saved at: " + filePath);
	}
}
