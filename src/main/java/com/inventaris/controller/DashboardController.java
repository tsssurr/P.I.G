package com.inventaris.controller;

import java.io.IOException;

import com.inventaris.App;
import com.inventaris.util.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {
    
    @FXML
    private Label lblWelcome;

    @FXML
    private VBox adminMenuContainer;

    @FXML
    public void initialize(){
        String username = Session.getCurrentUser().getUsername();
        String role = Session.getCurrentUser().getRole();
        lblWelcome.setText("Selamat Datang." + username + "(" + role + ")");
        
        if("user".equalsIgnoreCase(role)){
            adminMenuContainer.setVisible(false);
            adminMenuContainer.setManaged(false);
        } 
    }

    @FXML
    public void handleLogout() throws IOException{
        Session.logout();

        Stage stage = (Stage) lblWelcome.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("login.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login Inventaris");
    }

    @FXML
    private void menuBarang(){
        System.out.println("Buka Menu Barang");
        // TODO : Pindah ke Screen Barang Nanti
        // yang penting ada many to many
    }

    @FXML
    private void menuSupplier(){
        System.out.println("Buka Menu Supplier");
    }

    @FXML
    private void menuGudang() {
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            Parent root = FXMLLoader.load(App.class.getResource("gudang.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Manajemen Gudang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuPengguna() {
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            Parent root = FXMLLoader.load(App.class.getResource("pengguna.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Manajemen Pengguna");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuBarangMasuk(){
        System.out.println("Buka Transaksi Masuk (Pengadaan)");
    }

    @FXML
    private void menuBarangKeluar(){
        System.out.println("Buka Transaksi Keluar (Retur/Jual)");
    }
}
