package com.inventaris.controller;

import java.io.IOException;
import com.inventaris.App;
import com.inventaris.dao.PenggunaDAO;
import com.inventaris.model.Pengguna;
import com.inventaris.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController{
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private PenggunaDAO penggunaDAO = new PenggunaDAO();

    @FXML
    private void handleLogin(){
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if(user.isEmpty() || pass.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan Password tidak boleh kosong!");
            return;
        }

        Pengguna pengguna = penggunaDAO.login(user, pass);

        if(pengguna != null){
            Session.setCurrentUser(pengguna);
            
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Login Berhasil! Halo, " + pengguna.getUsername());
            
            try{
                FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
                Parent root = loader.load();

                Stage stage =(Stage) txtUsername.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("P.I.G - Dashboard");
                stage.centerOnScreen();
            }catch(IOException e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Gagal Memuat Halaman Dashboard");
            }
            
        }else{
            showAlert(Alert.AlertType.ERROR, "Error", "Username atau Password salah");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
