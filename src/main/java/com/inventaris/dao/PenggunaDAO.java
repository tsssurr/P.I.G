package com.inventaris.dao;

import java.sql.*;
import com.inventaris.DatabaseConnection;
import com.inventaris.model.Pengguna;

public class PenggunaDAO {
    public Pengguna login(String username, String password) {
        Pengguna user = null;
        String sql = "SELECT * FROM pengguna WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Pengguna();
                user.setIdPengguna(rs.getInt("id_pengguna"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                
                int idG = rs.getInt("id_gudang");
                if (rs.wasNull()) {
                    user.setIdGudang(0);
                } else {
                    user.setIdGudang(idG);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
