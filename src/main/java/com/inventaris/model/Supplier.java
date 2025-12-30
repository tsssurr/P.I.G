package com.inventaris.model;

public class Supplier{
    private int idSupplier;
    private String namaSupplier;
    private String alamat;
    private String kontak;

    public Supplier(int id, String nama, String al, String kon){
        this.idSupplier = id;
        this.namaSupplier = nama;
        this.alamat = al;
        this.kontak = kon;
    }

    public int getIdSupplier(){
        return idSupplier;
    }
    public String getNamaSupplier(){
        return namaSupplier;
    }
    public String getAlamat(){
        return alamat;
    }
    public String getKontak(){
        return kontak;
    }

    public void setNamaSupplier(String nama){
        this.namaSupplier = nama;
    }
    public void setAlamat(String al){
        this.alamat = al;
    }
    public void setKontak(String kon){
        this.kontak = kon;
    }
}

