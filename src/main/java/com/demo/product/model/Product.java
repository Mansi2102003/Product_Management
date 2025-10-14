package com.demo.product.model;


import java.math.BigDecimal;

 //Represents a single product record from the 'products' table.
public class Product {

    private int id;
    private String productCode;
    private String productName;
    private String category;
    private String uom;
    private BigDecimal price;

    public Product() {}

    public Product(String productCode, String productName, String category, String uom, BigDecimal price) {
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.uom = uom;
        this.price = price;
    }

    public Product(int id, String productCode, String productName, String category, String uom, BigDecimal price) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.uom = uom;
        this.price = price;
    }
    //Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getUom() { return uom; }
    public void setUom(String uom) { this.uom = uom; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "Product [code=" + productCode + ", name=" + productName +
                ", category=" + category + ", uom=" + uom + ", price=" + price + "]";
    }
}

