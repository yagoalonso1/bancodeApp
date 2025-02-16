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

/**
 * Controlador para la vista de depósito de dinero.
 * Gestiona las operaciones de depósito y la interfaz de usuario relacionada.
 * 
 * @author Yago
 * @version 1.0
 */
public class DepositoController {
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;

    /**
     * Inicializa el controlador.
     * Se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
        
        // Añadir listener para validar entrada en tiempo real
        cantidadField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                cantidadField.setText(oldValue);
            }
        });
    }

    /**
     * Maneja el proceso de depósito de dinero.
     * Verifica la cantidad y actualiza el saldo.
     */
    @FXML
    private void realizarDeposito() {
        try {
            String cantidadTexto = cantidadField.getText();
            
            // Verificar si el campo está vacío
            if (cantidadTexto.isEmpty()) {
                mostrarAlerta("Error", "Por favor ingrese una cantidad", Alert.AlertType.ERROR);
                return;
            }
            
            double cantidad = Double.parseDouble(cantidadTexto);
            
            // Verificar si la cantidad es negativa o cero
            if (cantidad <= 0) {
                mostrarAlerta("Error", "La cantidad debe ser mayor que 0", Alert.AlertType.ERROR);
                return;
            }
            
            cuenta.depositar(cantidad);
            mostrarAlerta("Depósito Exitoso", 
                "Depósito realizado correctamente\nNuevo saldo: " + cuenta.getSaldoFormateado(), 
                Alert.AlertType.INFORMATION);
            volverAlDashboard();
            
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese un número válido", Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al volver al dashboard", Alert.AlertType.ERROR);
        }
    }

    /**
     * Vuelve a la vista del dashboard.
     * @throws IOException Si hay un error al cargar la vista del dashboard
     */
    @FXML
    private void volverAlDashboard() throws IOException {
        Stage stage = (Stage) cantidadField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 500, 500);
        stage.setScene(scene);
    }

    /**
     * Muestra una alerta al usuario.
     * 
     * @param titulo Título de la alerta
     * @param mensaje Mensaje de la alerta
     * @param tipo Tipo de alerta (ERROR, INFORMATION, WARNING)
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}