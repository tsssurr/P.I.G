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

public class ReturController {

    @FXML private TableView<Retur> tableRetur;
    @FXML private TableColumn<Retur, LocalDate> colTanggal;
    @FXML private TableColumn<Retur, String> colBarang;
    @FXML private TableColumn<Retur, String> colGudang;
    @FXML private TableColumn<Retur, String> colSupplier; // Tujuan Retur
    @FXML private TableColumn<Retur, Integer> colJumlah;
    @FXML private TableColumn<Retur, String> colKeterangan;
    
    @FXML private DatePicker dateTanggal;
    @FXML private ComboBox<Barang> comboBarang;
    @FXML private ComboBox<Gudang> comboGudang;
    @FXML private ComboBox<Supplier> comboSupplier;
    @FXML private TextField txtJumlah;
    @FXML private TextArea txtKeterangan;
    
    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private ReturDAO returDAO = new ReturDAO();
    private BarangDAO barangDAO = new BarangDAO();
    private GudangDAO gudangDAO = new GudangDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();
    
    @FXML
    public void initialize() {
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colGudang.setCellValueFactory(new PropertyValueFactory<>("namaGudang"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        comboBarang.setItems(FXCollections.observableArrayList(barangDAO.getAllBarang()));
        comboGudang.setItems(FXCollections.observableArrayList(gudangDAO.getAllGudang()));
        comboSupplier.setItems(FXCollections.observableArrayList(supplierDAO.getAllSupplier()));

        dateTanggal.setValue(LocalDate.now());

        loadData();
    }

    private void loadData() {
        tableRetur.setItems(FXCollections.observableArrayList(returDAO.getAllRetur()));
    }

    @FXML
    private void handleSave() {
        if (comboBarang.getValue() == null || comboGudang.getValue() == null || 
            comboSupplier.getValue() == null || txtJumlah.getText().isEmpty()) {
            showAlert("Validasi", "Barang, Gudang, Supplier, dan Jumlah wajib diisi!");
            return;
        }

        try {
            boolean sukses = returDAO.simpanRetur(
                dateTanggal.getValue(),
                comboBarang.getValue().getIdBarang(),
                comboGudang.getValue().getIdGudang(),
                comboSupplier.getValue().getIdSupplier(),
                Integer.parseInt(txtJumlah.getText()),
                txtKeterangan.getText()
            );

            if (sukses) {
                showAlert("Sukses", "Data Retur berhasil disimpan!");
                clearForm();
                loadData();
            }else {
                showAlert("Gagal", "Terjadi kesalahan saat menyimpan data.");
            }
        }catch (NumberFormatException e) {
            showAlert("Error", "Jumlah harus berupa angka!");
        }
    }

    private void clearForm() {
        comboBarang.getSelectionModel().clearSelection();
        comboGudang.getSelectionModel().clearSelection();
        comboSupplier.getSelectionModel().clearSelection();
        txtJumlah.clear();
        txtKeterangan.clear();
        dateTanggal.setValue(LocalDate.now());
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) tableRetur.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }

}