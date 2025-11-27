package com.example.util;
import java.sql.*;
public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost/test";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root123";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
