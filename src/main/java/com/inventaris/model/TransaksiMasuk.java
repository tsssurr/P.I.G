package com.inventaris.model;

import java.time.LocalDate;

public class TransaksiMasuk{
    private int idMasuk;
    private LocalDate tanggal;
    private int idBarang;
    private int idSupplier;
    private int idGudang;
    private String namaBarang;
    private String namaSupplier; 
    private String namaGudang;
    private int jumlah;
    private String keterangan;

    public TransaksiMasuk(){}

    public TransaksiMasuk(int id, LocalDate tgl, String nmBarang, String nmSupplier, String nmGudang, int jml, String ket){
        this.idMasuk = id;
        this.tanggal = tgl;
        this.namaBarang = nmBarang;
        this.namaSupplier = nmSupplier;
        this.namaGudang = nmGudang;
        this.jumlah = jml;
        this.keterangan = ket;
    }

    public int getIdMasuk(){
        return idMasuk;
    }
    public LocalDate getTanggal(){
        return tanggal;
    }
    public int getIdBarang(){
        return idBarang;
    }
    public String getNamaBarang(){
        return namaBarang;
    }
    public int getIdSupplier(){
        return idSupplier;
    }
    public String getNamaSupplier(){
        return namaSupplier;
    }
    public int getIdGudang(){
        return idGudang;
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
    
    public void setIdBarang(int id){
        this.idBarang = id; 
    }
    public void setIdSupplier(int id){
        this.idSupplier = id;
    }
    public void setIdGudang(int id){ 
        this.idGudang = id;
    }
    public void setJumlah(int jumlah){
        this.jumlah = jumlah;
    }
    public void setTanggal(LocalDate tanggal){
        this.tanggal = tanggal;
    }
    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }
}