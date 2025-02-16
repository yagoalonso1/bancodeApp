package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.CuentaBancaria;
import models.Operacion;

public class HistorialController {
    @FXML private ListView<String> historialList;
    private CuentaBancaria cuenta;

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
        cargarHistorial();
    }

    private void cargarHistorial() {
        if (cuenta != null) {
            historialList.getItems().clear();
            for (Operacion operacion : cuenta.getHistorial()) {
                historialList.getItems().add(operacion.toString());
            }
        } else {
            historialList.getItems().add("No hay historial disponible.");
        }
    }

    @FXML
    private void volverAlDashboard() {
        try {
            Stage stage = (Stage) historialList.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}