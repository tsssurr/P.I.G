package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.GudangDAO;
import com.inventaris.dao.PenggunaDAO;
import com.inventaris.model.Gudang;
import com.inventaris.model.Pengguna;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PenggunaController{

    @FXML private TableView<Pengguna> tablePengguna;
    @FXML private TableColumn<Pengguna, String> colUsername;
    @FXML private TableColumn<Pengguna, String> colRole;
    @FXML private TableColumn<Pengguna, Integer> colGudang;

    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;

    @FXML private ComboBox<String> comboRole;
    @FXML private ComboBox<Gudang> comboGudang;

    private boolean isInputValid(){
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || comboRole.getValue() == null){
            showAlert("Validasi", "Username, Password, dan Role wajib diisi!");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String content){
        ButtonType yesButton = new ButtonType("Ya"); 
        ButtonType noButton = new ButtonType("Tidak");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, yesButton, noButton);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        
        return result.isPresent() && result.get() == yesButton;
    }

    private PenggunaDAO penggunaDAO = new PenggunaDAO();
    private GudangDAO gudangDAO = new GudangDAO();
    
    private ObservableList<Pengguna> userList = FXCollections.observableArrayList();
    private Pengguna selectedUser = null;

    @FXML
    public void initialize(){
        
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colGudang.setCellValueFactory(new PropertyValueFactory<>("idGudang"));

        
        comboRole.setItems(FXCollections.observableArrayList("admin", "user"));

        ObservableList<Gudang> listGudang = FXCollections.observableArrayList(gudangDAO.getAllGudang());

        Gudang pusat = new Gudang(0, "Pusat / Admin All", "-");
        listGudang.add(0, pusat);
        
        comboGudang.setItems(listGudang);

        loadData();

        tablePengguna.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->{
            if(newVal != null){
                selectedUser = newVal;
                txtUsername.setText(newVal.getUsername());
                txtPassword.setText(newVal.getPassword());
                comboRole.setValue(newVal.getRole());
                
                for(Gudang g : comboGudang.getItems()){
                    if(g.getIdGudang() == newVal.getIdGudang()){
                        comboGudang.setValue(g);
                        break;
                    }
                }
            }
        });
    }

    private void loadData(){
        userList.clear();
        userList.addAll(penggunaDAO.getAllPengguna());
        tablePengguna.setItems(userList);
    }

    @FXML
    private void handleSave(){
        if(isInputValid()){
            if(showConfirmation("Konfirmasi Simpan", "Simpan data pengguna?")){
                int idGudang = 0;
                if(comboGudang.getValue() != null){
                    idGudang = comboGudang.getValue().getIdGudang();
                }

                if(penggunaDAO.tambahPengguna(txtUsername.getText(), txtPassword.getText(), comboRole.getValue(), idGudang)){
                    showAlert("Sukses", "Pengguna berhasil dibuat");
                    handleClear();
                    loadData();
                }
            }
        }
    }

    @FXML
    private void handleUpdate(){
        if(selectedUser != null && isInputValid()){
            if(showConfirmation("Konfirmasi Perubahan", "Ubah data pengguna?")){
                int idGudang = 0;
                if(comboGudang.getValue() != null){
                    idGudang = comboGudang.getValue().getIdGudang();
                }

                if(penggunaDAO.updatePengguna(selectedUser.getIdPengguna(), txtUsername.getText(), txtPassword.getText(), comboRole.getValue(), idGudang)){
                    showAlert("Sukses", "Data pengguna berhasil diubah");
                    handleClear();
                    loadData();
                }
            }
        }else{
            showAlert("Peringatan", "Pilih pengguna terlebih dahulu!");
        }
    }

    @FXML
    private void handleDelete(){
        if(selectedUser != null){
            if(showConfirmation("Komfirmasi Hapus", "Hapus data pengguna " + selectedUser.getUsername() + "?")){
                if(penggunaDAO.hapusPengguna(selectedUser.getIdPengguna())){
                    showAlert("Sukses", "Pengguna berhasil dihapus");
                    handleClear();
                    loadData();
                }
            }
        }
    }

    @FXML
    private void handleClear(){
        txtUsername.clear();
        txtPassword.clear();
        comboRole.getSelectionModel().clearSelection();
        comboGudang.getSelectionModel().clearSelection();
        selectedUser = null;
        tablePengguna.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() throws IOException{
        Stage stage =(Stage) tablePengguna.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }
}