package com.demo.product.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//Handles PostgreSQL DB connection using properties file.

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/productdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mansi0210";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

