package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BancoDeYagoApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/inicio.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);
            primaryStage.setTitle("Banco de Yago");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de inicio: " + e.getMessage());
            e.printStackTrace();
        }
    }
     
    public static void main(String[] args) {
        launch();
    }
}