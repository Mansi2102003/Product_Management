package com.demo.product;
import java.math.BigDecimal;
import  java.util.*;

public class Product {
	private Integer id;
	private String productCode;
	private String productName;
	private String category;
	private String uom;
	private BigDecimal price;
	
	
	public Product() {
	}


	public Product(String productCode, String productName, String category, String uom, BigDecimal price) {
		super();
		this.productCode = productCode;
		this.productName = productName;
		this.category = category;
		this.uom = uom;
		this.price = price;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getUom() {
		return uom;
	}


	public void setUom(String uom) {
		this.uom = uom;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}if(o ==null || getClass()!= o.getClass()) {
			return false;
		}
		Product product = (Product)o;
		return Objects.equals(productCode, product.productCode);
	}
	
	@Override 
	public int hashCode() {
		return Objects.hashCode(productCode);
	}
	
	
	
}
