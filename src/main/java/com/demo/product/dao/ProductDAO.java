package com.demo.product.dao;

import com.demo.product.model.Product;
import com.demo.product.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM products WHERE productcode = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insert(Product p) {
    	
    	 if (p.getCategory() == null || p.getCategory().trim().isEmpty()) {
             throw new IllegalArgumentException("Category cannot be null or empty.");
         }
         if (p.getUom() == null || p.getUom().trim().isEmpty()) {
             throw new IllegalArgumentException("UOM cannot be null or empty.");
         }

        String sql = "INSERT INTO products (productcode, productname, category, uom, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getProductCode());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getUom());
            
            if (p.getPrice() != null) {
                ps.setDouble(4, p.getPrice());
            } else {
                ps.setNull(4, java.sql.Types.DOUBLE);
            }

            ps.setDouble(5, p.getPrice());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
