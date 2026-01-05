package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Retur;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// CRUD Retur
public class ReturDAO{

    // READ
    public List<Retur> getAllRetur(){
        List<Retur> list = new ArrayList<>();
        String sql = "SELECT r.*, b.nama_barang, g.nama_gudang, s.nama_supplier " +
                     "FROM retur r " +
                     "JOIN barang b ON r.id_barang = b.id_barang " +
                     "JOIN gudang g ON r.id_gudang = g.id_gudang " +
                     "JOIN supplier s ON r.id_supplier = s.id_supplier " +
                     "ORDER BY r.tanggal DESC";

        try(Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Retur(
                    rs.getDate("tanggal").toLocalDate(),
                    rs.getString("nama_barang"),
                    rs.getString("nama_gudang"),
                    rs.getInt("jumlah"),
                    rs.getString("keterangan"),
                    rs.getString("nama_supplier"),
                    rs.getInt("id_retur")
                ));
            }
        }catch(SQLException e){ e.printStackTrace(); }
        return list;
    }

    // CREATE
    public boolean simpanRetur(LocalDate tgl, int idBarang, int idGudang, int idSupplier, int jumlah, String ket){
        String sql = "INSERT INTO retur(tanggal, id_barang, id_gudang, id_supplier, jumlah, keterangan) VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setDate(1, Date.valueOf(tgl));
            ps.setInt(2, idBarang);
            ps.setInt(3, idGudang);
            ps.setInt(4, idSupplier);
            ps.setInt(5, jumlah);
            ps.setString(6, ket);
            
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}