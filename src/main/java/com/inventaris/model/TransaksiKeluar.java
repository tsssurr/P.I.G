package com.inventaris.model;

import java.time.LocalDate;

public class TransaksiKeluar{
    // private int idRetur;
    private LocalDate tanggal;
    private String namaBarang;
    private String namaGudang;
    // private String namaSupplier;
    private int jumlah;
    private String keterangan;
    
    // private int idBarang;
    // private int idGudang;
    // private int idSupplier;

    public TransaksiKeluar(LocalDate tgl, String brg, String gdg, int jml, String ket){
        this.tanggal = tgl;
        this.namaBarang = brg;
        this.namaGudang = gdg;
        this.jumlah = jml;
        this.keterangan = ket;
    }

    public LocalDate getTanggal(){
        return tanggal;
    }
    public String getNamaBarang(){
        return namaBarang;
    }
    public String getNamaGudang(){
        return namaGudang;
    }
    public int getJumlah(){
        return jumlah;
    }
    public String getKeterangan(){
        return keterangan;
    }
}