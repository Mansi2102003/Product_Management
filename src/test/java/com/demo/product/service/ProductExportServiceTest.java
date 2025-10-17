package com.demo.product.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.demo.product.dao.ProductDAO;
import com.demo.product.model.Product;

public class ProductExportServiceTest {
	@Mock
	private ProductDAO mockDAO;

	@InjectMocks
	private ProductExportService exportService;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExportProducts_createsExcelFile() {
		List<Product> mockProducts = List.of(new Product(1, "P001", "Pen", "Stationery", "PCS", 10.5),
				new Product(2, "P002", "Notebook", "Stationery", "PCS", null));

		when(mockDAO.findAll()).thenReturn(mockProducts);

		exportService.exportProducts();

		File dir = new File("./output");
		assertTrue(dir.exists(), "Output folder should exist");

		boolean foundExcel = false;
		for (File f : dir.listFiles()) {
			if (f.getName().startsWith("Product_Export_") && f.getName().endsWith(".xlsx")) {
				foundExcel = true;
				break;
			}
		}
		assertTrue(foundExcel, "Export Excel file should be generated");
	}
}
