package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.AppState;
import models.CuentaBancaria;

public class RetiroController {
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;
    private static final double UMBRAL_COMISION = 200.0;

    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
    }

    @FXML
    private void realizarRetiro() {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());
            
            // Convertir la cantidad a EUR para mostrar advertencia de comisión
            double cantidadEnEUR = cuenta.getDivisaActual().convertirAEUR(cantidad);
            
            if (cantidadEnEUR >= UMBRAL_COMISION) {
                Alert alertaComision = new Alert(Alert.AlertType.WARNING);
                alertaComision.setTitle("Aviso de Comisión");
                alertaComision.setHeaderText(null);
                alertaComision.setContentText("Se aplicará una comisión del 2% por retirar más de 200€");
                alertaComision.showAndWait();
            }

            if (cuenta.retirar(cantidad)) {
                mostrarAlerta("Retiro Exitoso", 
                    "Retiro realizado correctamente\nNuevo saldo: " + cuenta.getSaldoFormateado(), 
                    Alert.AlertType.INFORMATION);
                volverAlDashboard();
            } else {
                mostrarAlerta("Error", "Saldo insuficiente para realizar el retiro", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese un número válido", Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al volver al dashboard", Alert.AlertType.ERROR);
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
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}