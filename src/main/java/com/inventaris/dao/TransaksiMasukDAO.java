package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.TransaksiMasuk;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransaksiMasukDAO{
    // READ
    public List<TransaksiMasuk> getAllTransaksi(){
        List<TransaksiMasuk> list = new ArrayList<>();
        String sql = "SELECT t.*, b.nama_barang, s.nama_supplier, g.nama_gudang " +
                     "FROM transaksi_masuk t " +
                     "JOIN barang b ON t.id_barang = b.id_barang " +
                     "JOIN supplier s ON t.id_supplier = s.id_supplier " +
                     "JOIN gudang g ON t.id_gudang = g.id_gudang " +
                     "ORDER BY t.tanggal DESC";

        try(Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
                while(rs.next()){
                list.add(new TransaksiMasuk(
                    rs.getInt("id_masuk"),
                    rs.getDate("tanggal").toLocalDate(),
                    rs.getString("nama_barang"),
                    rs.getString("nama_supplier"),
                    rs.getString("nama_gudang"),
                    rs.getInt("jumlah"),
                    rs.getString("keterangan")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // CREATE
    public boolean simpanTransaksi(LocalDate tanggal, int idBarang, int idSupplier, int idGudang, int jumlah, String ket){
        String sql = "INSERT INTO transaksi_masuk(tanggal, id_barang, id_supplier, id_gudang, jumlah, keterangan) VALUES(?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setDate(1, Date.valueOf(tanggal));
            ps.setInt(2, idBarang);
            ps.setInt(3, idSupplier);
            ps.setInt(4, idGudang);
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