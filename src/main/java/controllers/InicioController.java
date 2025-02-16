package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioController {
    @FXML
    private Button loginButton;

    @FXML
    private Button registroButton;

    @FXML
    private void initialize() {
        // Este método se llama automáticamente después de cargar el FXML
        System.out.println("InicioController inicializado");
    }

    @FXML
    private void irALogin() {
        System.out.println("Botón Iniciar Sesión presionado");
        cambiarEscena("/views/login.fxml");
    }

    @FXML
    private void irARegistro() {
        System.out.println("Botón Registrarse presionado");
        cambiarEscena("/views/registro.fxml");
    }

    private void cambiarEscena(String ruta) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }
}