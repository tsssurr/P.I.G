package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.*;
import com.inventaris.model.*;
import com.inventaris.util.Session;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
    private boolean isInputValid() {
        if (comboBarang.getValue() == null || comboGudang.getValue() == null || 
            comboSupplier.getValue() == null || txtJumlah.getText().isEmpty()) {
            showAlert("Peringatan", "Semua kolom (kecuali keterangan) wajib diisi!");
            return false;
        }
        try {
            Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException e) {
            showAlert("Peringatan", "Jumlah harus berupa angka!");
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
    
    private ReturDAO returDAO = new ReturDAO();
    private BarangDAO barangDAO = new BarangDAO();
    private GudangDAO gudangDAO = new GudangDAO();
    private SupplierDAO supplierDAO = new SupplierDAO();
    private StokDAO stokDAO = new StokDAO();
    
    @FXML
    public void initialize() {
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colBarang.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colGudang.setCellValueFactory(new PropertyValueFactory<>("namaGudang"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("namaSupplier"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        comboBarang.setItems(FXCollections.observableArrayList(barangDAO.getAllBarang()));
        aturPilihanGudang();
        comboSupplier.setDisable(true);

        comboBarang.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            loadFilteredSupplier();
        });
        comboGudang.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            loadFilteredSupplier();
        });

        dateTanggal.setValue(LocalDate.now());

        loadData();
    }

    private void loadFilteredSupplier(){
        if (comboBarang.getValue() != null && comboGudang.getValue() != null) {
            
            int idBarang = comboBarang.getValue().getIdBarang();
            int idGudang = comboGudang.getValue().getIdGudang();

            List<Supplier> validSuppliers = supplierDAO.getSupplierByRiwayat(idBarang, idGudang);

            comboSupplier.setItems(FXCollections.observableArrayList(validSuppliers));
            
            if (validSuppliers.isEmpty()) {
                comboSupplier.setDisable(true);
                comboSupplier.setPromptText("Tidak ada supplier untuk barang ini di gudang ini");
            } else {
                comboSupplier.setDisable(false);
                comboSupplier.setPromptText("-- Pilih Supplier --");
            }
        } else {
            comboSupplier.getItems().clear();
            comboSupplier.setDisable(true);
        }
    }

    private void loadData() {
        tableRetur.setItems(FXCollections.observableArrayList(returDAO.getAllRetur()));
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if(showConfirmation("Konfirmasi", "Simpan transaksi retur?")){           
                try {
                    int jumlahKeluar = Integer.parseInt(txtJumlah.getText());
                    int idBarang =  comboBarang.getValue().getIdBarang();
                    int idGudang = comboGudang.getValue().getIdGudang();

                    int stokTersedia = stokDAO.getStok(idBarang, idGudang);

                    if(stokTersedia < jumlahKeluar){
                        showAlert("Peringatan", "Stok tidak cukup!");
                        return;
                    }

                    boolean sukses = returDAO.simpanRetur(
                        dateTanggal.getValue(),
                        comboBarang.getValue().getIdBarang(),
                        comboGudang.getValue().getIdGudang(),
                        comboSupplier.getValue().getIdSupplier(),
                        jumlahKeluar,
                        txtKeterangan.getText()
                    );

                    if (sukses) {
                        stokDAO.kurangiStok(idBarang, idGudang, jumlahKeluar);
                        showAlert("Sukses", "Data Retur berhasil disimpan!");
                        clearForm();
                        loadData();
                    }else {
                        showAlert("Gagal", "Terjadi kesalahan saat menyimpan data.");
                    }
                }catch (NumberFormatException e) {
                    showAlert("Peringatan", "Jumlah harus berupa angka!");
                }
            }else{
                showAlert("Peringatan", "Barang, Gudang, Supplier, dan Jumlah wajib diisi!");
                return;
            }
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

    private void aturPilihanGudang() {
        Pengguna user = Session.getCurrentUser();
        
        ObservableList<Gudang> semuaGudang = FXCollections.observableArrayList(gudangDAO.getAllGudang());

        if (user.getIdGudang() == 0) {
            comboGudang.setItems(semuaGudang);
            comboGudang.setDisable(false); 
        } else {
            for (Gudang g : semuaGudang) {
                if (g.getIdGudang() == user.getIdGudang()) {
                    comboGudang.setItems(FXCollections.observableArrayList(g));
                    
                    comboGudang.setValue(g);
                    
                    comboGudang.setDisable(true); 
                    break;
                }
            }
        }
    }
}