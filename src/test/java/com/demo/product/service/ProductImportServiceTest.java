package com.demo.product.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.demo.product.dao.ProductDAO;
import com.demo.product.model.Product;

class ProductImportServiceTest {

	private ProductDAO productDAO;
	private ProductImportService importService;

	@BeforeEach
	void setup() {
		productDAO = mock(ProductDAO.class);
		importService = new ProductImportService(productDAO);
	}

	@Test
	void testImportValidProducts() {
		// Arrange
		Product p1 = new Product(1, "P004", "Pen", "Stationery", "PCS", 10.5);

		// Mock method calls on the mock object
		when(productDAO.existsByCode("P004")).thenReturn(false);
		when(productDAO.insert(p1)).thenReturn(1);

		importService.importProducts("./src/test/resources/products_valid.xlsx");

		// Assert (you can verify interactions)
		verify(productDAO, times(1)).existsByCode("P004");
		verify(productDAO, times(1)).insert(p1);

	}

}