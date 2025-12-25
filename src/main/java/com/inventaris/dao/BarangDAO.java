package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//CRUD Barang
public class BarangDAO {
    // CREATE 
    public boolean tambahBarang(String kode, String nama, String kategori) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, kategori) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ps.setString(2, nama);
            ps.setString(3, kategori);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ 
    public List<Barang> getAllBarang() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Barang(
                    rs.getInt("id_barang"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE 
    public boolean updateBarang(int id, String kode, String nama, String kategori) {
        String sql = "UPDATE barang SET kode_barang=?, nama_barang=?, kategori=? WHERE id_barang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ps.setString(2, nama);
            ps.setString(3, kategori);
            ps.setInt(4, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE 
    public boolean hapusBarang(int id) {
        String sql = "DELETE FROM barang WHERE id_barang=?";
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