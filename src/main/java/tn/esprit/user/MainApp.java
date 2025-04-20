package tn.esprit.user;

import javafx.application.Application;
import javafx.stage.Stage;
import tn.esprit.user.controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/user/LoginScreen.fxml"));
        Scene scene = new Scene(loader.load(), 300, 250);
        LoginController controller = loader.getController();
        controller.setStage(primaryStage); // Ensure setStage is called
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}