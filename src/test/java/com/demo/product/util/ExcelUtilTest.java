package com.demo.product.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;
import org.junit.jupiter.api.Test;

import com.demo.product.model.Product;

//Test class to test  ExcelUtil - read and write excel
public class ExcelUtilTest {

	private static final String TEST_FILE = "./output/test_products.xlsx";

	@Test
	void testWriteAndReadExcel() {
		// Dumy list for products
		List<Product> products = List.of(new Product(1, "P001", "Pen", "Stationery", "PCS", 10.5),
				new Product(2, "P002", "Notebook", "Stationery", "PCS", 50.0));

		// Calls writeExcel method to test for Write an excel
		ExcelUtil.writeExcel(products, TEST_FILE);

		// Creates file with given file path
		File file = new File(TEST_FILE);
		assertTrue(file.exists(), "Excel file should be created");

		// Calls readExcel method to test read an excel
		List<Product> readProducts = ExcelUtil.readExcel(TEST_FILE);
		assertEquals(2, readProducts.size());
		assertEquals("P001", readProducts.get(0).getProductCode());
	}
}
