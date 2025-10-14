package com.demo.product.util;

import com.demo.product.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ExcelUtil {

    public static List<Product> readExcel(String filePath) {
        List<Product> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            int rowNum = 0;
            for (Row row : sheet) {
                if (rowNum++ == 0) continue; // skip header
                Product p = new Product();

                p.setProductCode(getCellString(row.getCell(0)));
                p.setProductName(getCellString(row.getCell(1)));
                p.setCategory(getCellString(row.getCell(2)));
                p.setUom(getCellString(row.getCell(3)));
                String priceStr = getCellString(row.getCell(4));
                if (!priceStr.isEmpty()) {
                    p.setPrice(new BigDecimal(priceStr));
                }

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    public static void writeExcel(List<Product> products, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");
            Row header = sheet.createRow(0);
            String[] cols = {"ProductCode", "ProductName", "Category", "UOM", "Price"};
            for (int i = 0; i < cols.length; i++) header.createCell(i).setCellValue(cols[i]);

            int rowNum = 1;
            for (Product p : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getProductCode());
                row.createCell(1).setCellValue(p.getProductName());
                row.createCell(2).setCellValue(p.getCategory());
                row.createCell(3).setCellValue(p.getUom());
                row.createCell(4).setCellValue(p.getPrice().toString());
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
