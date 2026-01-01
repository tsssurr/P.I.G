package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.*;
import com.inventaris.model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TransaksiMasukController {
    
    @FXML private TableView<TransaksiMasuk> tableMasuk;
    @FXML private TableColumn<TransaksiMasuk, LocalDate> colTanggal;
    @FXML private TableColumn<TransaksiMasuk, String> colBarang;
    @FXML private TableColumn<TransaksiMasuk, String> colSupplier;
    @FXML private TableColumn<TransaksiMasuk, String> colGudang;
    @FXML private TableColumn<TransaksiMasuk, Integer> colJumlah;
    @FXML private TableColumn<TransaksiMasuk, String> colKeterangan;
    
    @FXML private DatePicker dateTanggal;
    @FXML private ComboBox<Barang> comboBarang;
    @FXML private ComboBox<Supplier> comboSupplier;
    @FXML private ComboBox<Gudang> comboGudang;
    @FXML private TextField txtJumlah;
    @FXML private TextArea txtKeterangan;
    
    private boolean isInputValid() {
        if (comboBarang.getValue() == null || comboSupplier.getValue() == null || 
            comboGudang.getValue() == null || txtJumlah.getText().isEmpty()) {
            showAlert("Validasi", "Semua kolom (kecuali keterangan) wajib diisi!");
            return false;
        }
        try {
            Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException e) {
            showAlert("Validasi", "Jumlah harus berupa angka!");
            return false;
        }
        return true;
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
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
    
    private TransaksiMasukDAO transaksiDAO = new TransaksiMasukDAO();
    private BarangDAO barangDAO = new BarangDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();
    private GudangDAO gudangDAO = new GudangDAO();

    @FXML
    public void initialize() {
        
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colGudang.setCellValueFactory(new PropertyValueFactory<>("namaGudang"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        comboBarang.setItems(FXCollections.observableArrayList(barangDAO.getAllBarang()));
        comboSupplier.setItems(FXCollections.observableArrayList(supplierDAO.getAllSupplier()));
        comboGudang.setItems(FXCollections.observableArrayList(gudangDAO.getAllGudang()));

        dateTanggal.setValue(LocalDate.now());

        loadData();
    }

    private void loadData() {
        tableMasuk.setItems(FXCollections.observableArrayList(transaksiDAO.getAllTransaksi()));
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (showConfirmation("Konfirmasi", "Simpan transaksi barang masuk?")) {
                
                boolean sukses = transaksiDAO.simpanTransaksi(
                    dateTanggal.getValue(),
                    comboBarang.getValue().getIdBarang(),
                    comboSupplier.getValue().getIdSupplier(),
                    comboGudang.getValue().getIdGudang(),
                    Integer.parseInt(txtJumlah.getText()),
                    txtKeterangan.getText()
                );

                if (sukses) {
                    showAlert("Sukses", "Transaksi berhasil disimpan!");
                    clearForm();
                    loadData();
                } else {
                    showAlert("Gagal", "Terjadi kesalahan saat menyimpan.");
                }
            }
        }
    }

    private void clearForm() {
        comboBarang.getSelectionModel().clearSelection();
        comboSupplier.getSelectionModel().clearSelection();
        comboGudang.getSelectionModel().clearSelection();
        txtJumlah.clear();
        txtKeterangan.clear();
        dateTanggal.setValue(LocalDate.now());
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) tableMasuk.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }

}