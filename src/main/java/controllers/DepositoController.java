package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import models.CuentaBancaria;
import models.AppState;

public class DepositoController {
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;

    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
    }

    @FXML
    private void depositarDinero() {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());

            if (cantidad > 0) {
                if (cuenta != null) {
                    cuenta.depositar(cantidad);
                    mostrarAlerta("Depósito Exitoso", 
                                  "Has depositado: " + cantidad + " " + 
                                  cuenta.getDivisaActual().getSimbolo(), 
                                  Alert.AlertType.INFORMATION);
                    volverAlDashboard();
                }
            } else {
                mostrarAlerta("Error", "Ingrese una cantidad válida.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException | IOException e) {
            mostrarAlerta("Error", "Por favor ingrese un número válido.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlDashboard() throws IOException {
        Stage stage = (Stage) cantidadField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 500, 500);
        stage.setScene(scene);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }
}