package com.inventaris.model;

import java.time.LocalDate;

public class Penjualan extends TransaksiKeluar{
    
    private int idPenjualan;
    private String namaPembeli;

    public Penjualan(LocalDate tgl, String brg, String gdg, int jml, String ket, String nama, int id){
        super(tgl, brg, gdg, jml, ket);
        this.namaPembeli = nama;
        this.idPenjualan = id;
    }

    public String getNamaPembeli(){
        return this.namaPembeli;
    }
    public int getIdRetur(){
        return this.idPenjualan;
    }

}