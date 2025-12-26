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
        try{
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            Parent root = FXMLLoader.load(App.class.getResource("barang.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Manajemen Barang");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void menuSupplier(){
        System.out.println("Buka Menu Supplier");
        try {
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            Parent root = FXMLLoader.load(App.class.getResource("supplier.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Manajemen Supplier");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuGudang(){
        System.out.println("Buka Menu Gudang");
    }

    @FXML
    private void menuPengguna(){
        System.out.println("Buka Menu Pengguna");
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
