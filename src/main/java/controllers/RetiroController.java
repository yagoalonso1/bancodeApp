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

public class RetiroController {
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;

    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
    }

    @FXML
    private void retirarDinero() {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());

            if (cantidad > 0) {
                if (cuenta != null && cuenta.retirar(cantidad)) {
                    mostrarAlerta("Retiro Exitoso", 
                                  "Has retirado: " + cantidad + " " + 
                                  cuenta.getDivisaActual().getSimbolo(), 
                                  Alert.AlertType.INFORMATION);
                    volverAlDashboard();
                } else {
                    mostrarAlerta("Error", "Saldo insuficiente.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Ingrese una cantidad válida.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese un número válido.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cambiar de pantalla. Inténtelo de nuevo.", Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error inesperado. Contacte con soporte.", Alert.AlertType.ERROR);
            e.printStackTrace();
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