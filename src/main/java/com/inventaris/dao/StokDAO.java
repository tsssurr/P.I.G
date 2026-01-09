package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import java.sql.*;

public class StokDAO {
    // Cek jumlah stok saat ini untuk validasi transaksi keluar
    public int getStok(int idBarang, int idGudang) {
        int stok = 0;
        String sql = "SELECT stok FROM stok_gudang WHERE id_barang = ? AND id_gudang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBarang);
            ps.setInt(2, idGudang);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stok = rs.getInt("stok");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stok;
    }

    // Tambah Stok untuk transaksi barang masuk
    public boolean tambahStok(int idBarang, int idGudang, int jumlah) {
        String sql = "INSERT INTO stok_gudang (id_barang, id_gudang, stok) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE stok = stok + ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBarang);
            ps.setInt(2, idGudang);
            ps.setInt(3, jumlah);
            ps.setInt(4, jumlah);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kurangi stok untuk transaksi keluar (retur & penjualan)
    public boolean kurangiStok(int idBarang, int idGudang, int jumlah) {
        String sql = "UPDATE stok_gudang SET stok = stok - ? WHERE id_barang = ? AND id_gudang = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, jumlah);
            ps.setInt(2, idBarang);
            ps.setInt(3, idGudang);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}