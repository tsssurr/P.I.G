package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.*;
import com.inventaris.model.*;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PenjualanController{
    
    @FXML private TableView<Penjualan> tablePenjualan;
    @FXML private TableColumn<Penjualan, LocalDate> colTanggal;
    @FXML private TableColumn<Penjualan, String> colBarang;
    @FXML private TableColumn<Penjualan, String> colGudang;
    @FXML private TableColumn<Penjualan, String> colPembeli;
    @FXML private TableColumn<Penjualan, Integer> colJumlah;
    @FXML private TableColumn<Penjualan, String> colKeterangan;
    
    @FXML private DatePicker dateTanggal;
    @FXML private ComboBox<Barang> comboBarang;
    @FXML private ComboBox<Gudang> comboGudang;
    @FXML private TextField txtJumlah;
    @FXML private TextArea txtKeterangan;
    
    @FXML private TextField txtNamaPembeli;
    @FXML private TextArea txtAlamatPembeli;
    @FXML private TextField txtKontakPembeli;
    
    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private PenjualanDAO penjualanDAO = new PenjualanDAO();
    private BarangDAO barangDAO = new BarangDAO();
    private GudangDAO gudangDAO = new GudangDAO();
    private PembeliDAO pembeliDAO = new PembeliDAO();
    
    @FXML
    public void initialize(){
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colGudang.setCellValueFactory(new PropertyValueFactory<>("namaGudang"));
        colPembeli.setCellValueFactory(new PropertyValueFactory<>("namaPembeli"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        
        comboBarang.setItems(FXCollections.observableArrayList(barangDAO.getAllBarang()));
        comboGudang.setItems(FXCollections.observableArrayList(gudangDAO.getAllGudang()));

        dateTanggal.setValue(LocalDate.now());
        loadData();
    }

    private void loadData(){
        tablePenjualan.setItems(FXCollections.observableArrayList(penjualanDAO.getAllPenjualan()));
    }

    @FXML
    private void handleSave(){
        if (comboBarang.getValue() == null || comboGudang.getValue() == null || 
            txtJumlah.getText().isEmpty() || txtNamaPembeli.getText().isEmpty()){
            showAlert("Validasi", "Barang, Gudang, Jumlah, dan Nama Pembeli wajib diisi!");
            return;
        }

        try{
            int idPembeliBaru = pembeliDAO.simpanPembeliBaru(
                txtNamaPembeli.getText(),
                txtKontakPembeli.getText(),
                txtAlamatPembeli.getText()
            );

            if (idPembeliBaru == -1){
                showAlert("Error", "Gagal menyimpan data pembeli! Cek koneksi database.");
                return;
            }

            boolean sukses = penjualanDAO.simpanPenjualan(
                dateTanggal.getValue(),
                comboBarang.getValue().getIdBarang(),
                comboGudang.getValue().getIdGudang(),
                idPembeliBaru, // <--- ID dari langkah nomor 2
                Integer.parseInt(txtJumlah.getText()),
                txtKeterangan.getText()
            );

            if (sukses){
                showAlert("Sukses", "Penjualan berhasil dicatat!");
                clearForm();
                loadData();
            }else{
                showAlert("Gagal", "Gagal menyimpan transaksi penjualan.");
            }

        }catch (NumberFormatException e){
            showAlert("Error", "Jumlah harus berupa angka!");
        }
    }

    private void clearForm(){
        comboBarang.getSelectionModel().clearSelection();
        comboGudang.getSelectionModel().clearSelection();
        txtJumlah.clear();
        txtKeterangan.clear();
        
        txtNamaPembeli.clear();
        txtAlamatPembeli.clear();
        txtKontakPembeli.clear();
        
        dateTanggal.setValue(LocalDate.now());
    }

    @FXML
    private void handleBack() throws IOException{
        Stage stage = (Stage) tablePenjualan.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }

}