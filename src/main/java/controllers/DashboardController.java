package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.AppState;
import models.CuentaBancaria;
import models.Divisa;

/**
 * Controlador para la vista principal del dashboard.
 * Gestiona las operaciones principales y la visualización del saldo.
 * 
 * @author Yago
 * @version 1.0
 */
public class DashboardController {
    @FXML private Label saldoLabel;
    @FXML private ComboBox<Divisa> divisaComboBox;
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;

    /**
     * Inicializa el controlador y configura los elementos de la interfaz.
     */
    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
        divisaComboBox.getItems().addAll(Divisa.values());
        divisaComboBox.setValue(cuenta.getDivisaActual());
        divisaComboBox.setOnAction(e -> cambiarDivisa());
        
        // Añadir listener para validar entrada
        cantidadField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                cantidadField.setText(oldValue);
            }
        });
        
        actualizarSaldo();
    }

    /**
     * Cambia la divisa actual y actualiza la visualización del saldo.
     */
    private void cambiarDivisa() {
        if (divisaComboBox.getValue() != null) {
            cuenta.cambiarDivisa(divisaComboBox.getValue());
            actualizarSaldo();
        }
    }

    /**
     * Actualiza la etiqueta del saldo con el valor actual.
     */
    private void actualizarSaldo() {
        saldoLabel.setText("Saldo: " + cuenta.getSaldoFormateado());
    }

    /**
     * Maneja el proceso de depósito de dinero.
     */
    @FXML
    private void realizarDeposito() {
        procesarOperacion(true);
    }

    /**
     * Maneja el proceso de retiro de dinero.
     */
    @FXML
    private void realizarRetiro() {
        procesarOperacion(false);
    }

    /**
     * Procesa una operación de depósito o retiro.
     * @param esDeposito true si es depósito, false si es retiro
     */
    private void procesarOperacion(boolean esDeposito) {
        try {
            double cantidad = Double.parseDouble(cantidadField.getText());
            
            // Verificar si la cantidad es negativa o cero
            if (cantidad <= 0) {
                mostrarAlerta("Error", "La cantidad debe ser mayor que 0", Alert.AlertType.ERROR);
                return;
            }

            if (esDeposito) {
                cuenta.depositar(cantidad);
                actualizarSaldo();
                cantidadField.clear();
                mostrarAlerta(
                    "Depósito Exitoso",
                    "Operación realizada correctamente",
                    Alert.AlertType.INFORMATION
                );
            } else {
                if (cuenta.retirar(cantidad)) {
                    actualizarSaldo();
                    cantidadField.clear();
                    mostrarAlerta(
                        "Retiro Exitoso",
                        "Operación realizada correctamente",
                        Alert.AlertType.INFORMATION
                    );
                } else {
                    mostrarAlerta("Error", "Saldo insuficiente", Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese un número válido", Alert.AlertType.ERROR);
        }
    }

    /**
     * Navega a la vista del historial de operaciones.
     */
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

    /**
     * Cierra la sesión actual y vuelve a la pantalla de inicio.
     */
    @FXML
    private void cerrarSesion() {
        try {
            // Reiniciar el estado de la aplicación
            AppState.getInstance().setCuentaActual(null);
            
            // Volver a la pantalla de inicio
            Stage stage = (Stage) saldoLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/inicio.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);
            stage.setScene(scene);
            
            mostrarAlerta("Sesión Cerrada", "Has cerrado sesión correctamente", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cerrar la sesión", Alert.AlertType.ERROR);
        }
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

    // Métodos para cambiar de vista (Retiro, Depósito, Historial)
    @FXML
    private void irADeposito() {
        cambiarVista("/views/deposito.fxml");
    }

    @FXML
    private void irARetiro() {
        cambiarVista("/views/retiro.fxml");
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