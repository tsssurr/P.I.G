package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.SupplierDAO;
import com.inventaris.model.Supplier;
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

public class SupplierController{

    @FXML private TableView<Supplier> tableSupplier;
    @FXML private TableColumn<Supplier, String> colNama;
    @FXML private TableColumn<Supplier, String> colAlamat;
    @FXML private TableColumn<Supplier, String> colKontak;
    
    @FXML private TextField txtNama;
    @FXML private TextArea txtAlamat;
    @FXML private TextField txtKontak;
    
    private boolean isInputValid(){
        if(txtNama.getText().isEmpty() || txtAlamat.getText().isEmpty()){
            showAlert("Validasi", "Nama dan Alamat wajib diisi!");
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

    private SupplierDAO supplierDAO = new SupplierDAO();
    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
    private Supplier selectedSupplier = null;
    
    @FXML
    public void initialize(){
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colKontak.setCellValueFactory(new PropertyValueFactory<>("kontak"));

        loadData();

        tableSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if(newSelection != null){
                selectedSupplier = newSelection;

                txtNama.setText(newSelection.getNamaSupplier());
                txtAlamat.setText(newSelection.getAlamat());
                txtKontak.setText(newSelection.getKontak());
            }
        });
    }

    private void loadData(){
        supplierList.clear();
        supplierList.addAll(supplierDAO.getAllSupplier());
        tableSupplier.setItems(supplierList);
    }

    @FXML
    private void handleSave(){
        if(isInputValid()){
            if(showConfirmation("Konfirmasi Simpan", "Simpan data supplier baru?")){
                if(supplierDAO.tambahSupplier(txtNama.getText(), txtAlamat.getText(), txtKontak.getText())){
                    showAlert("Sukses", "Supplier berhasil disimpan");
                    handleClear();
                    loadData();
                }
            }
        }
    }

    @FXML
    private void handleUpdate(){
        if(selectedSupplier != null && isInputValid()){
            if(showConfirmation("Konfirmasi Perubahan", "Ubah data supplier?")){
                if(supplierDAO.updateSupplier(selectedSupplier.getIdSupplier(), txtNama.getText(), txtAlamat.getText(), txtKontak.getText())){
                    showAlert("Sukses", "Data supplier berhasil diubah");
                    handleClear();
                    loadData();
                }
            }
        }else{
            showAlert("Peringatan", "Pilih supplier terlebih dahulu");
        }
    }

    @FXML
    private void handleDelete(){
        if(selectedSupplier != null){
            String nama = selectedSupplier.getNamaSupplier();
            if(showConfirmation("Konfirmasi Hapus", "Hapus data supplier \"" + nama + "\"?")){
                if(supplierDAO.hapusSupplier(selectedSupplier.getIdSupplier())){
                    showAlert("Sukses", "Supplier berhasil dihapus");
                    handleClear();
                    loadData();
                }
            }
        }else{
            showAlert("Peringatan", "Pilih supplier pada  terlebih dahulu");
        }
    }

    @FXML
    private void handleClear(){
        txtNama.clear();
        txtAlamat.clear();
        txtKontak.clear();
        selectedSupplier = null;
        tableSupplier.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() throws IOException{
        Stage stage =(Stage) tableSupplier.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }

}