package com.inventaris.model;

import java.time.LocalDate;

public class Retur extends TransaksiKeluar{
    
    private int idRetur;
    private String namaSupplier;

    public Retur(LocalDate tgl, String brg, String gdg, int jml, String ket, String nama, int id){
        super(tgl, brg, gdg, jml, ket);
        this.namaSupplier = nama;
        this.idRetur = id;
    }

    public String getNamaSupplier(){
        return this.namaSupplier;
    }
    public int getIdRetur(){
        return this.idRetur;
    }

}