package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//CRUD SUPPLIER
public class SupplierDAO{
    // READ
    public List<Supplier> getAllSupplier(){
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE status = 1";
        try(Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Supplier(
                    rs.getInt("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("alamat"),
                    rs.getString("kontak")
                ));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // CREATE
    public boolean tambahSupplier(String nama, String alamat, String kontak){
        String sql = "INSERT INTO supplier(nama_supplier, alamat, kontak) VALUES(?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, nama);
            ps.setString(2, alamat);
            ps.setString(3, kontak);
            ps.executeUpdate();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateSupplier(int id, String nama, String alamat, String kontak){
        String sql = "UPDATE supplier SET nama_supplier=?, alamat=?, kontak=? WHERE id_supplier=?";
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, nama);
            ps.setString(2, alamat);
            ps.setString(3, kontak);
            ps.setInt(4, id);
            ps.executeUpdate();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    // sama seperti barang
    // delet untuk supplier di sini hanya akan mengubah statusnya menjadi tidak aktif (0)
    public boolean hapusSupplier(int id){
        String sql = "UPDATE supplier SET status=0 WHERE id_supplier=?";
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Ini method supaya penguna hanya bisa melakukan retur hanya kepada supplier yang pernah menyuplai saja
    public List<Supplier> getSupplierByRiwayat(int idBarang, int idGudang) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT DISTINCT s.* " +
                     "FROM supplier s " +
                     "JOIN transaksi_masuk tm ON s.id_supplier = tm.id_supplier " +
                     "WHERE tm.id_barang = ? AND tm.id_gudang = ? " +
                     "ORDER BY s.nama_supplier";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBarang);
            ps.setInt(2, idGudang);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Supplier(
                    rs.getInt("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("alamat"),
                    rs.getString("kontak")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}