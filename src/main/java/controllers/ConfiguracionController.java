package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class ConfiguracionController {
    @FXML private ChoiceBox<String> monedaChoiceBox;

    @FXML
    private void initialize() {
        monedaChoiceBox.getItems().addAll("€ - Euro", "$ - Dólar", "£ - Libra");
        monedaChoiceBox.setValue("€ - Euro");
    }

    @FXML
    private void cambiarMoneda() throws IOException {
        String monedaSeleccionada = monedaChoiceBox.getValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cambio de Moneda");
        alert.setHeaderText("Moneda cambiada a: " + monedaSeleccionada);
        alert.showAndWait();

        volverAlDashboard();
    }

    private void volverAlDashboard() throws IOException {
        Stage stage = (Stage) monedaChoiceBox.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 500, 500);
        stage.setScene(scene);
    }
}