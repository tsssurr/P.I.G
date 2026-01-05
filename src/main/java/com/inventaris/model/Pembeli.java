package com.inventaris.model;

public class Pembeli{
    
    private int idPembeli;
    private String namaPembeli;
    private String kontak;
    private String alamat;
    
    public Pembeli(int id, String nama, String kontak, String alamat){
        this.idPembeli = id;
        this.namaPembeli = nama;
        this. kontak = kontak;
        this.alamat = alamat;
    }

    public Pembeli(int id, String nama, String alamat){
        this.idPembeli = id;
        this.namaPembeli = nama;
        this.alamat = alamat;
    }

    public int getIdPembeli(){
        return idPembeli;
    }
    public String getNamaPembeli(){
        return namaPembeli;
    }
    public String getKontakPembeli(){
        return kontak;
    }
    public String getAlamatPembeli(){
        return alamat;
    }

    @Override
    public String toString(){
        return namaPembeli;
    }
}