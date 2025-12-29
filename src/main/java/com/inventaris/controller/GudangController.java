package com.inventaris.controller;

import com.inventaris.App;
import com.inventaris.dao.GudangDAO;
import com.inventaris.model.Gudang;
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

public class GudangController {

    @FXML private TableView<Gudang> tableGudang;
    @FXML private TableColumn<Gudang, String> colNama;
    @FXML private TableColumn<Gudang, String> colLokasi;

    @FXML private TextField txtNama;
    @FXML private TextArea txtLokasi;

    private boolean isInputValid() {
        if (txtNama.getText().isEmpty()) {
            showAlert("Validasi", "Nama Gudang wajib diisi!");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String content) {
        ButtonType yesButton = new ButtonType("Ya"); 
        ButtonType noButton = new ButtonType("Tidak");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, yesButton, noButton);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        
        return result.isPresent() && result.get() == yesButton;
    }
    
    private GudangDAO gudangDAO = new GudangDAO();
    private ObservableList<Gudang> gudangList = FXCollections.observableArrayList();
    private Gudang selectedGudang = null;

    @FXML
    public void initialize() {

        colNama.setCellValueFactory(new PropertyValueFactory<>("namaGudang"));
        colLokasi.setCellValueFactory(new PropertyValueFactory<>("lokasi"));

        loadData();

        tableGudang.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedGudang = newVal;
                txtNama.setText(newVal.getNamaGudang());
                txtLokasi.setText(newVal.getLokasi());
            }
        });
    }

    private void loadData() {
        gudangList.clear();
        gudangList.addAll(gudangDAO.getAllGudang());
        tableGudang.setItems(gudangList);
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            if (showConfirmation("Konfirmasi Simpan", "Simpan data gudang baru?")) {
                if (gudangDAO.tambahGudang(txtNama.getText(), txtLokasi.getText())) {
                    showAlert("Sukses", "Gudang berhasil disimpan");
                    handleClear();
                    loadData();
                }
            }
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedGudang != null && isInputValid()) {
            if (showConfirmation("Konfirmasi Update", "Ubah data gudang ini?")) {
                if (gudangDAO.updateGudang(selectedGudang.getIdGudang(), txtNama.getText(), txtLokasi.getText())) {
                    showAlert("Sukses", "Gudang berhasil diupdate");
                    handleClear();
                    loadData();
                }
            }
        } else {
            showAlert("Peringatan", "Pilih gudang dulu!");
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedGudang != null) {
            String nama = selectedGudang.getNamaGudang();
            if (showConfirmation("Konfirmasi Hapus", "Yakin hapus gudang \"" + nama + "\"?\nPERHATIAN: Data stok di gudang ini mungkin akan hilang.")) {
                if (gudangDAO.hapusGudang(selectedGudang.getIdGudang())) {
                    showAlert("Sukses", "Gudang berhasil dihapus");
                    handleClear();
                    loadData();
                }
            }
        } else {
            showAlert("Peringatan", "Pilih gudang dulu!");
        }
    }

    @FXML
    private void handleClear() {
        txtNama.clear();
        txtLokasi.clear();
        selectedGudang = null;
        tableGudang.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleBack() throws IOException {
        Stage stage = (Stage) tableGudang.getScene().getWindow();
        Parent root = FXMLLoader.load(App.class.getResource("dashboard.fxml"));
        stage.setScene(new Scene(root));
    }
}