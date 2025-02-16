package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

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
            // Obtener el Stage actual desde cualquiera de los botones
            Stage stage = null;
            if (loginButton != null) {
                stage = (Stage) loginButton.getScene().getWindow();
            } else if (registroButton != null) {
                stage = (Stage) registroButton.getScene().getWindow();
            } else {
                // Intenta obtener la ventana activa de otra manera
                stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            }

            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);

            // Establecer la nueva escena
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }
}