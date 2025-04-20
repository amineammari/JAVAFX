package tn.esprit.user.entities;

import tn.esprit.user.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.user.controllers.*;

import java.io.IOException;

public class Navigation {
    private final Stage stage;

    public Navigation(Stage stage) {
        this.stage = stage;
    }

    private void navigate(String fxmlPath, String title, double width, double height, Runnable controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            controllerSetup.run(loader.getController());
        } catch (IOException e) {
            showError("Erreur de navigation", "Impossible de charger l'écran : " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void navigateToLogin() {
        navigate("/tn/esprit/user/LoginScreen.fxml", "Connexion", 300, 250, () -> {
            LoginController controller = (LoginController) stage.getScene().<FXMLLoader>getRoot().getProperties().get("controller");
            controller.setStage(stage);
        });
    }

    public void navigateToRegister() {
        navigate("/tn/esprit/user/RegisterScreen.fxml", "Inscription", 300, 300, () -> {
            RegisterController controller = (RegisterController) stage.getScene().<FXMLLoader>getRoot().getProperties().get("controller");
            controller.setStage(stage);
        });
    }

    public void navigateToProfile(User user) {
        navigate("/tn/esprit/user/ProfileScreen.fxml", "Profil", 300, 400, () -> {
            ProfileController controller = (ProfileController) stage.getScene().<FXMLLoader>getRoot().getProperties().get("controller");
            controller.setUser(user);
            controller.setStage(stage);
        });
    }

    public void navigateToAdmin(User admin) {
        navigate("/tn/esprit/user/AdminScreen.fxml", "Administration", 600, 400, () -> {
            AdminController controller = (AdminController) stage.getScene().<FXMLLoader>getRoot().getProperties().get("controller");
            controller.setAdmin(admin);
            controller.setStage(stage);
        });
    }

    public void navigateToMedecin(User medecin) {
        navigate("/tn/esprit/user/ProfileScreen.fxml", "Espace Médecin", 300, 400, () -> {
            ProfileController controller = (ProfileController) stage.getScene().<FXMLLoader>getRoot().getProperties().get("controller");
            controller.setUser(medecin);
            controller.setStage(stage);
        });
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
