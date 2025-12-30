package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Gudang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//CRUD GUDANG
public class GudangDAO {

    // READ
    public List<Gudang> getAllGudang() {
        List<Gudang> list = new ArrayList<>();
        String sql = "SELECT * FROM gudang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Gudang(
                    rs.getInt("id_gudang"),
                    rs.getString("nama_gudang"),
                    rs.getString("lokasi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // CREATE
    public boolean tambahGudang(String nama, String lokasi) {
        String sql = "INSERT INTO gudang (nama_gudang, lokasi) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, lokasi);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean updateGudang(int id, String nama, String lokasi) {
        String sql = "UPDATE gudang SET nama_gudang=?, lokasi=? WHERE id_gudang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, lokasi);
            ps.setInt(3, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean hapusGudang(int id) {
        String sql = "DELETE FROM gudang WHERE id_gudang=?";
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