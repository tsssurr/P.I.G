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

    // READ
    public java.util.List<Pengguna> getAllPengguna() {
        java.util.List<Pengguna> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM pengguna"; 
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pengguna p = new Pengguna();
                p.setIdPengguna(rs.getInt("id_pengguna"));
                p.setUsername(rs.getString("username"));
                p.setPassword(rs.getString("password"));
                p.setRole(rs.getString("role"));
                
                int idG = rs.getInt("id_gudang");
                if (rs.wasNull()) p.setIdGudang(0);
                else p.setIdGudang(idG);
                
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // CREATE
    public boolean tambahPengguna(String user, String pass, String role, int idGudang) {
        String sql = "INSERT INTO pengguna (username, password, role, id_gudang) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, role);
            
            if (idGudang == 0) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, idGudang);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updatePengguna(int id, String user, String pass, String role, int idGudang) {
        String sql = "UPDATE pengguna SET username=?, password=?, role=?, id_gudang=? WHERE id_pengguna=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, role);
            
            if (idGudang == 0) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, idGudang);
            
            ps.setInt(5, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean hapusPengguna(int id) {
        String sql = "DELETE FROM pengguna WHERE id_pengguna=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
