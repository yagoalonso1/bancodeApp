package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import models.CuentaBancaria;
import models.AppState;
import models.Divisa;

public class DashboardController {
    @FXML private Label saldoLabel;
    @FXML private ComboBox<Divisa> divisaComboBox;
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;

    @FXML
    private void initialize() {
        // Obtener la cuenta del estado de la aplicación
        cuenta = AppState.getInstance().getCuentaActual();

        // Configurar el ComboBox de divisas
        divisaComboBox.getItems().addAll(Divisa.values());
        divisaComboBox.setValue(cuenta.getDivisaActual());

        // Añadir listener para cambios en la divisa
        divisaComboBox.setOnAction(e -> cambiarDivisa());

        actualizarSaldo();
    }

    private void cambiarDivisa() {
        if (divisaComboBox.getValue() != null) {
            cuenta.cambiarDivisa(divisaComboBox.getValue());
            actualizarSaldo();
        }
    }

    private void actualizarSaldo() {
        saldoLabel.setText("Saldo: " + cuenta.getSaldoFormateado());
    }

    @FXML
    private void realizarRetiro() {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());
            if (cuenta.retirar(cantidad)) {
                actualizarSaldo();
                mostrarAlerta("Éxito", "Retiro realizado correctamente", Alert.AlertType.INFORMATION);
                cantidadField.clear();
            } else {
                mostrarAlerta("Error", "Saldo insuficiente", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese un número válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void realizarDeposito() {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());
            cuenta.depositar(cantidad);
            actualizarSaldo();
            mostrarAlerta("Éxito", "Depósito realizado correctamente", Alert.AlertType.INFORMATION);
            cantidadField.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor, ingrese un número válido", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Métodos para cambiar de vista (Retiro, Depósito, Historial, Configuración)
    @FXML
    private void irADeposito() {
        cambiarVista("/views/deposito.fxml");
    }

    @FXML
    private void irARetiro() {
        cambiarVista("/views/retiro.fxml");
    }

    @FXML
    private void irAConfiguracion() {
        cambiarVista("/views/configuracion.fxml");
    }

    @FXML
    private void irAHistorial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/historial.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);

            HistorialController historialController = loader.getController();
            historialController.setCuenta(cuenta);

            Stage stage = (Stage) saldoLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de historial", Alert.AlertType.ERROR);
        }
    }

    private void cambiarVista(String ruta) {
        try {
            Stage stage = (Stage) saldoLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Scene scene = new Scene(loader.load(), 500, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cambiar de vista", Alert.AlertType.ERROR);
        }
    }
}