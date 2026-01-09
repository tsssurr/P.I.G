package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.BarangDAO;
import com.inventaris.model.Barang;
import java.io.IOException;
import java.util.List;
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


public class BarangController{

    @FXML private TableView<Barang> tableBarang;
    @FXML private TableColumn<Barang, String> colKode;
    @FXML private TableColumn<Barang, String> colNama;
    @FXML private TableColumn<Barang, String> colKategori;

    @FXML private TextField txtKode;
    @FXML private TextField txtNama;
    @FXML private TextField txtKategori;

    private boolean isInputValid(){
        if(txtKode.getText().isEmpty() || txtNama.getText().isEmpty() || txtKategori.getText().isEmpty()){
            showAlert("Peringatan", "Semua kolom harus diisi!");
            return false;
        }
        return true;
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

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private BarangDAO barangDAO = new BarangDAO();
    private ObservableList<Barang> barangList = FXCollections.observableArrayList();
    private Barang selectedBarang = null; 

    @FXML
    public void initialize(){
        
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeBarang"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        loadData();

        tableBarang.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if(newSelection != null){
                selectedBarang = newSelection;
                txtKode.setText(newSelection.getKodeBarang());
                txtNama.setText(newSelection.getNamaBarang());
                txtKategori.setText(newSelection.getKategori());
            }
        });
    }

    private void loadData(){
        barangList.clear();
        List<Barang> data = barangDAO.getAllBarang();
        barangList.addAll(data);
        tableBarang.setItems(barangList);
    }

    @FXML
    private void handleSave(){
        if(isInputValid()){
            if(showConfirmation("Konfirmasi Simpan", "Simpan data barang baru?")){
                boolean sukses = barangDAO.tambahBarang(txtKode.getText(), txtNama.getText(), txtKategori.getText());
                if(sukses){
                    showAlert("Sukses", "Barang berhasil ditambahkan");
                    handleClear();
                    loadData();
                }else{
                    showAlert("Gagal", "Gagal menambahkan barang");
                }
            }
        }
    }

    @FXML
    private void handleUpdate(){
        if(selectedBarang != null && isInputValid()){
            if(showConfirmation("Konfirmasi Perubahan", "Ubah data barang?")){
                boolean sukses = barangDAO.updateBarang(selectedBarang.getIdBarang(), txtKode.getText(), txtNama.getText(), txtKategori.getText());
                if(sukses){
                    showAlert("Sukses", "Data baang berhasil diubah");
                    handleClear();
                    loadData();
                }
            }
        }else{
            showAlert("Peringatan", "Pilih barang terlebih dahulu!");
        }
    }

    @FXML
    private void handleDelete(){
        if(selectedBarang != null){
            if(showConfirmation("Konfirmasi Hapus", "Hapus data barang?")){
                boolean sukses = barangDAO.hapusBarang(selectedBarang.getIdBarang());
                if(sukses){
                    showAlert("Sukses", "Barang berhasil dihapus");
                    handleClear();
                    loadData();
                }
            }
        }else{
            showAlert("Peringatan", "Pilih barang terlebih dahulu!");
        }
    }

    @FXML
    private void handleClear(){
        txtKode.clear();
        txtNama.clear();
        txtKategori.clear();
        selectedBarang = null;
        tableBarang.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() throws IOException{
        Stage stage =(Stage) tableBarang.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }

}