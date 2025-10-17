package com.demo.product.dao;

import com.demo.product.model.Product;
import com.demo.product.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Directly communicate with Database using JDBC 
//Handles SQL queries
public class ProductDAO {

	// This method will check for duplicate records
	public boolean existsByCode(String code) {
		String sql = "SELECT COUNT(*) FROM products WHERE productcode = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// This method contains the logic of import data into database
	public int insert(Product p) {

		// if category is null it will throw an exception
		if (p.getCategory() == null || p.getCategory().trim().isEmpty()) {
			throw new IllegalArgumentException("Category cannot be null or empty.");
		}

		// if UOM is null it will throw an exception
		if (p.getUom() == null || p.getUom().trim().isEmpty()) {
			throw new IllegalArgumentException("UOM cannot be null or empty.");
		}

		// Insert statement: Insert values to database
		String sql = "INSERT INTO products (productcode, productname, category, uom, price) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, p.getProductCode());
			ps.setString(2, p.getProductName());
			ps.setString(3, p.getCategory());
			ps.setString(4, p.getUom());

			// Handles null values
			if (p.getPrice() != null) {
				ps.setDouble(5, p.getPrice());
			} else {
				ps.setNull(5, java.sql.Types.DECIMAL);
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Retrieves All data from database and return the list of objects
	public List<Product> findAll() {

		// List to store Product type object which contains row data
		List<Product> list = new ArrayList<>();
		String sql = "SELECT * FROM products ORDER BY id";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			// Iterates over rows
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setProductCode(rs.getString("productcode"));
				p.setProductName(rs.getString("productname"));
				p.setCategory(rs.getString("category"));
				p.setUom(rs.getString("uom"));
				java.math.BigDecimal priceValue = rs.getBigDecimal("price");
				if (priceValue != null) {
					p.setPrice(priceValue.doubleValue());
				} else {
					p.setPrice(null);
				}
				// Adds One row to list
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Returns Lists of All Rows
		return list;
	}

}
