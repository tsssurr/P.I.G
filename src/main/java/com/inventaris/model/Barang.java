package com.inventaris.model;

public class Barang {
    private int idBarang;
    private String kodeBarang;
    private String namaBarang;
    private String kategori;

    public Barang(int id, String kode, String nama, String kategori){
        this.idBarang = id;
        this.kodeBarang = kode;
        this.namaBarang = nama;
        this.kategori = kategori;
    }

    public int getIdBarang(){
        return idBarang;
    }
    public String getKodeBarang(){
        return kodeBarang;
    }
    public String getNamaBarang(){
        return namaBarang;
    }
    public String getKategori(){
        return kategori;
    }

    public void setKodeBarang(String kode){
        this.kodeBarang = kode;
    }
    public void setNameBarang(String nama){
        this.namaBarang = nama;
    }
    public void setKategore(String kategori){
        this.kategori = kategori;
    }
}
