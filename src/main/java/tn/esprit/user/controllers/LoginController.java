package tn.esprit.user.controllers;

import tn.esprit.user.entities.Navigation;
import tn.esprit.user.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.user.services.Userservice;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Userservice userService = new Userservice();
    private Navigation navigation;

    public void setStage(Stage stage) {
        this.navigation = new Navigation(stage);
    }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            User user = userService.login(email, password);
            if (user != null) {
                String roles = user.getRole();
                if (roles.contains("ROLE_ADMIN")) {
                    navigation.navigateToAdmin(user);
                } else if (roles.contains("ROLE_MEDCIN")) {
                    navigation.navigateToMedecin(user);
                } else {
                    navigation.navigateToProfile(user); // Patient
                }
            } else {
                errorLabel.setText("Email ou mot de passe incorrect, ou compte bloqué.");
            }
        } catch (SQLException e) {
            errorLabel.setText("Erreur de connexion à la base de données.");
            e.printStackTrace();
        }
    }

    @FXML
    public void goToRegister() {
        navigation.navigateToRegister();
    }
}