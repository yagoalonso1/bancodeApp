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

/**
 * Controlador para la vista de registro de nuevos usuarios.
 * Gestiona el proceso de registro y validación de datos.
 * 
 * @author Yago
 * @version 1.0
 */
public class RegistroController {
    @FXML private TextField nombreField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registrarButton;
    @FXML private Button volverButton;
    @FXML private TextField respuestaField;
    
    /** Lista estática que almacena todos los usuarios registrados */
    private static List<Usuario> usuarios = new ArrayList<>();

    /**
     * Obtiene la lista de usuarios registrados.
     * @return Lista de usuarios
     */
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Maneja el proceso de registro de un nuevo usuario.
     * Valida los campos y crea un nuevo usuario si todo es correcto.
     */
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

        usuarios.add(new Usuario(nombre, password, respuesta));
        mostrarAlerta("Registro Exitoso", "Usuario registrado correctamente.", Alert.AlertType.INFORMATION);
        volverAlMenu();
    }

    /**
     * Verifica si un usuario ya existe en el sistema.
     * 
     * @param nombre Nombre de usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     */
    private boolean usuarioExiste(String nombre) {
        return usuarios.stream().anyMatch(u -> u.getNombre().equals(nombre));
    }

    /**
     * Vuelve a la vista del menú principal.
     */
    @FXML
    private void volverAlMenu() {
        try {
            Stage stage = (Stage) registrarButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/inicio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver al menú principal.", Alert.AlertType.ERROR);
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
}