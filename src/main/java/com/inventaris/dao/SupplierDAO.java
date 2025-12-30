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
        String sql = "SELECT * FROM supplier";
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
    public boolean hapusSupplier(int id){
        String sql = "DELETE FROM supplier WHERE id_supplier=?";
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
}