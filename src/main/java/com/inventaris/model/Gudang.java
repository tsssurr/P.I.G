package com.inventaris.model;

public class Gudang {
    private int idGudang;
    private String namaGudang;
    private String lokasi;

    public Gudang(int id, String nama, String lokasi) {
        this.idGudang = id;
        this.namaGudang = nama;
        this.lokasi = lokasi;
    }

    public int getIdGudang() { return idGudang; }
    public String getNamaGudang() { return namaGudang; }
    public String getLokasi() { return lokasi; }

    public void setNamaGudang(String nama) { this.namaGudang = nama; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    @Override
    public String toString() {
        return namaGudang;
    }
}