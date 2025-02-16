package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import models.AppState;
import models.CuentaBancaria;
import models.Divisa;

import java.io.IOException;

/**
 * Controlador para la vista de configuración.
 * Gestiona las preferencias y configuraciones del usuario.
 * 
 * @author Yago
 * @version 1.0
 */
public class ConfiguracionController {
    @FXML private ChoiceBox<String> monedaChoiceBox;
    private CuentaBancaria cuenta;

    /**
     * Inicializa el controlador y configura los elementos de la interfaz.
     */
    @FXML
    private void initialize() {
        cuenta = AppState.getInstance().getCuentaActual();
        monedaChoiceBox.getItems().addAll("€ - Euro", "$ - Dólar", "£ - Libra");
        monedaChoiceBox.setValue("€ - Euro");
    }

    /**
     * Guarda los cambios de configuración y vuelve al dashboard.
     */
    @FXML
    private void cambiarMoneda() throws IOException {
        String monedaSeleccionada = monedaChoiceBox.getValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cambio de Moneda");
        alert.setHeaderText("Moneda cambiada a: " + monedaSeleccionada);
        alert.showAndWait();

        cuenta.cambiarDivisa(Divisa.valueOf(monedaSeleccionada));
        mostrarAlerta("Éxito", "Configuración guardada correctamente", Alert.AlertType.INFORMATION);
        volverAlDashboard();
    }

    /**
     * Vuelve a la vista del dashboard sin guardar cambios.
     */
    @FXML
    private void volverAlDashboard() throws IOException {
        Stage stage = (Stage) monedaChoiceBox.getScene().getWindow();
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