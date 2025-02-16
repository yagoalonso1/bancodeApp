package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Usuario;

public class RegistroController {
    @FXML private TextField nombreField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registrarButton;
    @FXML private Button volverButton;
    @FXML private TextField respuestaField;

    // Lista estática de usuarios
    private static List<Usuario> usuarios = new ArrayList<>();

    /**
     * Devuelve la lista de usuarios registrados.
     */
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    @FXML
    private void registrarUsuario() {
        String nombre = nombreField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String respuesta = respuestaField.getText();

        if (nombre.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || respuesta.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }

        if (usuarioExiste(nombre)) {
            mostrarAlerta("Error", "El usuario ya existe.", Alert.AlertType.ERROR);
            return;
        }

        // Registrar el usuario
        usuarios.add(new Usuario(nombre, password, respuesta));
        mostrarAlerta("Registro Exitoso", "Usuario registrado correctamente.", Alert.AlertType.INFORMATION);
        volverAlMenu();
    }

    private boolean usuarioExiste(String nombre) {
        return usuarios.stream().anyMatch(u -> u.getNombre().equals(nombre));
    }

    @FXML
    private void volverAlMenu() {
        cambiarEscena("/views/inicio.fxml");
    }

    private void cambiarEscena(String ruta) {
        try {
            Stage stage = (Stage) registrarButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cambiar de pantalla.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}