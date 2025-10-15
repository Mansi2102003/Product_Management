package com.demo.product.util;

import com.demo.product.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelUtil {

	public static List<Product> readExcel(String filePath) {
		List<Product> list = new ArrayList<>();

		// It opens the file in reading mode
		try (FileInputStream fis = new FileInputStream(filePath);
				// Workbook is a interface of Apache POI
				// XSSFWorkbook is a class of Workbook interface
				Workbook workbook = new XSSFWorkbook(fis)) {

			// sheet is reference variable of Sheet interface.
			// getSheetAt(0) is used to retrieve first sheet from workbook.
			Sheet sheet = workbook.getSheetAt(0);

			// rowNun is a variable and its value is 0 means reading excel start with row
			// number 0
			int rowNum = 0;

			// for each loop iterates over each rows of sheet
			for (Row row : sheet) {
				// skip header
				if (rowNum++ == 0) {
					continue;
				}

				// Product class object
				Product p = new Product();

				/*
				 * Get the first cell of the current row (row.getCell(0)). Extract the string
				 * value from that cell (getCellString(...)). Assign the resulting string value
				 * to the productCode field of the p object (p.setProductCode(...))
				 */
				p.setProductCode(getCellString(row.getCell(0)));
				p.setProductName(getCellString(row.getCell(1)));
				p.setCategory(getCellString(row.getCell(2)));
				p.setUom(getCellString(row.getCell(3)));

				String priceStr = getCellString(row.getCell(4));
				if (!priceStr.isEmpty()) {
					try {
						p.setPrice(Double.parseDouble(priceStr));
					} catch (NumberFormatException e) {
						p.setPrice(null);
					}
				} else {
					p.setPrice(null);
				}

				// Adds row into List
				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// returns the list
		return list;
	}

	private static String getCellString(Cell cell) {
		if (cell == null)
			return "";
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue().trim();
	}

	public static void writeExcel(List<Product> products, String filePath) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Products");
			Row header = sheet.createRow(0);
			String[] cols = { "ProductCode", "ProductName", "Category", "UOM", "Price" };
			for (int i = 0; i < cols.length; i++)
				header.createCell(i).setCellValue(cols[i]);

			int rowNum = 1;
			for (Product p : products) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(p.getProductCode());
				row.createCell(1).setCellValue(p.getProductName());
				row.createCell(2).setCellValue(p.getCategory());
				row.createCell(3).setCellValue(p.getUom());
				if (p.getPrice() != null)
					row.createCell(4).setCellValue(p.getPrice());
				else
					row.createCell(4).setCellValue("");
			}

			File file = new File(filePath);
			file.getParentFile().mkdirs();
			try (FileOutputStream fos = new FileOutputStream(file)) {
				workbook.write(fos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}