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

/**
 * Controlador para la vista de inicio de sesión.
 * Gestiona la autenticación de usuarios y la recuperación de contraseñas.
 * 
 * @author Yago
 * @version 1.0
 */
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

    /**
     * Inicializa el controlador.
     * Se ejecuta automáticamente después de cargar el FXML.
     */
    @FXML
    private void initialize() {
        System.out.println("LoginController inicializado");
    }

    /**
     * Maneja el proceso de autenticación del usuario.
     * Verifica las credenciales y permite el acceso si son correctas.
     */
    @FXML
    private void autenticarUsuario() {
        System.out.println("Intentando autenticar usuario");
        String usuarioIngresado = usuarioField.getText();
        String passwordIngresado = passwordField.getText();

        if (usuarioIngresado.isEmpty() || passwordIngresado.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingrese usuario y contraseña.", Alert.AlertType.ERROR);
            return;
        }

        if ("admin".equals(usuarioIngresado) && "1234".equals(passwordIngresado)) {
            loginExitoso();
            return;
        }

        Usuario usuarioEncontrado = buscarUsuario(usuarioIngresado);
        if (usuarioEncontrado != null && usuarioEncontrado.autenticar(passwordIngresado)) {
            loginExitoso();
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Busca un usuario en la lista de usuarios registrados.
     * 
     * @param nombre Nombre del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    private Usuario buscarUsuario(String nombre) {
        return RegistroController.getUsuarios().stream()
                .filter(u -> u.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    /**
     * Procesa el inicio de sesión exitoso.
     * Crea una nueva cuenta si es necesario y navega al dashboard.
     */
    private void loginExitoso() {
        if (AppState.getInstance().getCuentaActual() == null) {
            AppState.getInstance().setCuentaActual(new CuentaBancaria(1000.0));
        }
        cambiarEscena("/views/dashboard.fxml");
        mostrarAlerta("Éxito", "Inicio de sesión exitoso.", Alert.AlertType.INFORMATION);
    }

    /**
     * Vuelve a la vista del menú principal.
     */
    @FXML
    private void volverAlMenu() {
        System.out.println("Volviendo al menú principal");
        cambiarEscena("/views/inicio.fxml");
    }

    /**
     * Muestra el diálogo de recuperación de contraseña.
     */
    @FXML
    private void mostrarRecuperacionPassword() {
        System.out.println("Mostrando recuperación de contraseña");
        try {
            dialogStage = new Stage();
            dialogStage.setTitle("Recuperar Contraseña");

            VBox dialogVbox = new VBox(10);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setPadding(new Insets(20));

            Label preguntaLabel = new Label("¿Cómo se llamaba tu mejor amigo de la infancia?");
            TextField usuarioField = new TextField();
            usuarioField.setPromptText("Usuario");
            TextField respuestaField = new TextField();
            respuestaField.setPromptText("Respuesta");

            Button recuperarButton = new Button("Recuperar Contraseña");
            recuperarButton.setOnAction(e -> recuperarPassword(
                usuarioField.getText(), 
                respuestaField.getText()
            ));

            dialogVbox.getChildren().addAll(
                new Label("Ingrese su usuario:"),
                usuarioField,
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

    /**
     * Procesa la recuperación de contraseña.
     * 
     * @param usuario Nombre de usuario
     * @param respuesta Respuesta a la pregunta de seguridad
     */
    private void recuperarPassword(String usuario, String respuesta) {
        Usuario usuarioEncontrado = buscarUsuario(usuario);
        if (usuarioEncontrado != null && usuarioEncontrado.verificarRespuestaSeguridad(respuesta)) {
            mostrarAlerta("Recuperación Exitosa", "Su contraseña es: " + usuarioEncontrado.getPassword(), Alert.AlertType.INFORMATION);
            dialogStage.close();
        } else {
            mostrarAlerta("Error", "Usuario o respuesta incorrectos.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Cambia la escena actual por una nueva.
     * 
     * @param ruta Ruta del archivo FXML a cargar
     */
    private void cambiarEscena(String ruta) {
        try {
            Stage stage = null;
            if (loginButton != null) {
                stage = (Stage) loginButton.getScene().getWindow();
            } else if (volverButton != null) {
                stage = (Stage) volverButton.getScene().getWindow();
            } else if (usuarioField != null) {
                stage = (Stage) usuarioField.getScene().getWindow();
            } else {
                stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            }

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