package com.inventaris.dao;

import com.inventaris.DatabaseConnection;
import com.inventaris.model.Penjualan; 
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// CRUD Penjualan
public class PenjualanDAO{
    // READ
    public List<Penjualan> getAllPenjualan(){
        List<Penjualan> list = new ArrayList<>();
        String sql = "SELECT p.*, b.nama_barang, g.nama_gudang, pm.nama_pembeli " +
                     "FROM penjualan p " +
                     "JOIN barang b ON p.id_barang = b.id_barang " +
                     "JOIN gudang g ON p.id_gudang = g.id_gudang " +
                     "JOIN pembeli pm ON p.id_pembeli = pm.id_pembeli " +
                     "ORDER BY p.tanggal DESC";

        try(Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                list.add(new Penjualan(
                    rs.getDate("tanggal").toLocalDate(),
                    rs.getString("nama_barang"),
                    rs.getString("nama_gudang"),
                    rs.getInt("jumlah"),
                    rs.getString("keterangan"),
                    rs.getString("nama_pembeli"),
                    rs.getInt("id_penjualan")
                ));
            }
        }catch(SQLException e){ e.printStackTrace(); }
        return list;
    }

    // CREATE
    public boolean simpanPenjualan(LocalDate tgl, int idBarang, int idGudang, int idPembeli, int jumlah, String ket){
        String sql = "INSERT INTO penjualan(tanggal, id_barang, id_gudang, id_pembeli, jumlah, keterangan) VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setDate(1, Date.valueOf(tgl));
            ps.setInt(2, idBarang);
            ps.setInt(3, idGudang);
            ps.setInt(4, idPembeli);
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