package controllers;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.AppState;
import models.CuentaBancaria;
import models.Usuario;

public class LoginController {
    @FXML private TextField usuarioField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button volverButton;
    @FXML private Button olvidoPasswordButton;
    private Stage dialogStage;

    /**
     * Obtiene la lista de usuarios registrados desde RegistroController.
     */
    private List<Usuario> getUsuarios() {
        return RegistroController.getUsuarios();
    }

    @FXML
    private void initialize() {
        System.out.println("LoginController inicializado");
    }

    @FXML
    private void autenticarUsuario() {
        String usuarioIngresado = usuarioField.getText();
        String passwordIngresado = passwordField.getText();

        // Validación de campos vacíos
        if (usuarioIngresado.isEmpty() || passwordIngresado.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingrese usuario y contraseña.", Alert.AlertType.ERROR);
            return;
        }

        // Intentar con usuario admin (usuario por defecto)
        if ("admin".equals(usuarioIngresado) && "1234".equals(passwordIngresado)) {
            if (AppState.getInstance().getCuentaActual() == null) {
                AppState.getInstance().setCuentaActual(new CuentaBancaria(1000.0));
            }
            cambiarEscena("/views/dashboard.fxml");
            return;
        }

        // Verificar con los usuarios registrados
        Usuario usuarioEncontrado = buscarUsuario(usuarioIngresado);
        if (usuarioEncontrado != null && usuarioEncontrado.autenticar(passwordIngresado)) {
            if (AppState.getInstance().getCuentaActual() == null) {
                AppState.getInstance().setCuentaActual(new CuentaBancaria(1000.0));
            }
            cambiarEscena("/views/dashboard.fxml");
        } else {
            mostrarAlerta("Error de Autenticación", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    private Usuario buscarUsuario(String nombre) {
        List<Usuario> usuarios = RegistroController.getUsuarios();

        if (usuarios == null || usuarios.isEmpty()) {
            return null; // No hay usuarios registrados
        }

        return usuarios.stream()
                .filter(u -> u.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    @FXML
    private void volverAlMenu() {
        cambiarEscena("/views/inicio.fxml");
    }

    @FXML
    private void mostrarRecuperacion() {
        try {
            // Crear un diálogo emergente para recuperación de contraseña
            dialogStage = new Stage();
            VBox dialogVbox = new VBox(10);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setPadding(new Insets(20));

            TextField usuarioRecuperacion = new TextField();
            usuarioRecuperacion.setPromptText("Nombre de Usuario");
            TextField respuestaField = new TextField();
            respuestaField.setPromptText("Respuesta de seguridad");

            Label preguntaLabel = new Label("¿Cuál es tu color favorito?");
            Button recuperarButton = new Button("Recuperar Contraseña");

            recuperarButton.setOnAction(e -> recuperarPassword(
                    usuarioRecuperacion.getText(),
                    respuestaField.getText()
            ));

            dialogVbox.getChildren().addAll(
                    new Label("Recuperación de Contraseña"),
                    usuarioRecuperacion,
                    preguntaLabel,
                    respuestaField,
                    recuperarButton
            );

            Scene dialogScene = new Scene(dialogVbox, 300, 250);
            dialogStage.setScene(dialogScene);
            dialogStage.show();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la recuperación de contraseña.", Alert.AlertType.ERROR);
        }
    }

    private void recuperarPassword(String usuario, String respuesta) {
        Usuario usuarioEncontrado = buscarUsuario(usuario);

        if (usuarioEncontrado != null && usuarioEncontrado.verificarRespuestaSeguridad(respuesta)) {
            mostrarAlerta("Recuperación Exitosa", "Su contraseña es: " + usuarioEncontrado.getPassword(), Alert.AlertType.INFORMATION);
            dialogStage.close();
        } else {
            mostrarAlerta("Error", "Usuario o respuesta incorrectos.", Alert.AlertType.ERROR);
        }
    }

    private void cambiarEscena(String ruta) {
        try {
            Stage stage = obtenerStageActual();
            if (stage == null) {
                mostrarAlerta("Error", "No se encontró la ventana activa.", Alert.AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cambiar de pantalla.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Obtiene la ventana actual para cambiar de escena de forma segura.
     */
    private Stage obtenerStageActual() {
        try {
            if (loginButton != null) {
                return (Stage) loginButton.getScene().getWindow();
            } else if (volverButton != null) {
                return (Stage) volverButton.getScene().getWindow();
            } else if (usuarioField != null) {
                return (Stage) usuarioField.getScene().getWindow();
            } else {
                return (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            }
        } catch (Exception e) {
            return null;
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