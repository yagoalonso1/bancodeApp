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
 * Controlador para la vista de retiro de dinero.
 * Maneja las operaciones de retiro y la interfaz de usuario relacionada.
 * 
 * @author Yago
 * @version 1.0
 */
public class RetiroController {
    @FXML private TextField cantidadField;
    private CuentaBancaria cuenta;
    /** Umbral a partir del cual se aplica comisión (en EUR) */
    private static final double UMBRAL_COMISION = 200.0;

    /**
     * Inicializa el controlador.
     * Se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
    }

    /**
     * Maneja el proceso de retiro de dinero.
     * Verifica la cantidad, aplica comisiones si corresponde y actualiza el saldo.
     */
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