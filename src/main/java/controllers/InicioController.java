package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controlador para la vista inicial de la aplicación.
 * Gestiona la navegación hacia el login y registro.
 * 
 * @author Yago
 * @version 1.0
 */
public class InicioController {
    @FXML
    private Button loginButton;

    @FXML
    private Button registroButton;

    /**
     * Inicializa el controlador.
     */
    @FXML
    private void initialize() {
        // Este método se llama automáticamente después de cargar el FXML
        System.out.println("InicioController inicializado");
    }

    /**
     * Navega a la vista de inicio de sesión.
     */
    @FXML
    private void irALogin() {
        System.out.println("Botón Iniciar Sesión presionado");
        cambiarEscena("/views/login.fxml");
    }

    /**
     * Navega a la vista de registro.
     */
    @FXML
    private void irARegistro() {
        System.out.println("Botón Registrarse presionado");
        cambiarEscena("/views/registro.fxml");
    }

    /**
     * Cambia la escena actual por una nueva.
     * 
     * @param ruta Ruta del archivo FXML a cargar
     */
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