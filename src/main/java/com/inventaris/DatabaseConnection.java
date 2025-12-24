package com.inventaris;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_inventaris_gudang";
    private static final String USER = "root";
    private static final String PASS = "tsssurr"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi Berhasil");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}