package com.demo.product.main;

import com.demo.product.service.ProductImportService;

public class ImportProductApp {
    public static void main(String[] args) {
    	String filePath = "/home/mansi/eclipse-workspace/Product_Master/src/main/resources/Product_Master_NewData.xlsx";
            new ProductImportService().importProducts(filePath);
        
}
}

