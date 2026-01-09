package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Pembeli;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CRUD Pembeli
public class PembeliDAO{
    // READ
    public List<Pembeli> getAllPembeli(){
        List<Pembeli> list = new ArrayList<>();
        String sql = "SELECT * FROM pembeli";
        try(Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Pembeli(rs.getInt("id_pembeli"), rs.getString("nama_pembeli"), rs.getString("kontak"), rs.getString("alamat")));
            }
        }catch(SQLException e){ e.printStackTrace(); }
        return list;
    }

    // CREATE
    public int simpanPembeliBaru(String nama, String kontak, String alamat){
        String sql = "INSERT INTO pembeli(nama_pembeli, kontak, alamat) VALUES(?, ?, ?)";
        int generatedId = -1;

        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setString(1, nama);
            ps.setString(2, kontak);
            ps.setString(3, alamat);
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    generatedId = rs.getInt(1);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return generatedId;
    }
}