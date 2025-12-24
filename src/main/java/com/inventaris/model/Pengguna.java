package com.inventaris.model;

public class Pengguna {
    private int idPengguna;
    private String username;
    private String password;
    private String role;
    private int idGudang; 

    public Pengguna(){}

    public Pengguna(int id, String user, String pass, String role, int idGudang){
        this.idPengguna = id;
        this.username = user;
        this.password = pass;
        this.role = role;
        this.idGudang = idGudang;
    }

    public int getIdPengguna(){ return idPengguna; }
    public String getUsername(){ return username; }
    public String getPassword(){ return password; }
    public String getRole(){ return role; }
    public int getIdGudang(){ return idGudang; }
    
    public void setIdPengguna(int id){ this.idPengguna = id; }
    public void setUsername(String user){ this.username = user; }
    public void setPassword(String pass){ this.password = pass; }
    public void setRole(String role){ this.role = role; }
    public void setIdGudang(int idGudang){ this.idGudang = idGudang; }
}
