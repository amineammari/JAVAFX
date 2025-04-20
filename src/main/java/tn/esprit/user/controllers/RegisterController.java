package tn.esprit.user.controllers;

import tn.esprit.user.entities.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.user.services.Userservice;
import tn.esprit.user.entities.Navigation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField photoField;

    @FXML
    private TextField telephoneField;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Label errorLabel;

    private Userservice userService = new Userservice();
    private Navigation navigation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ChoiceBox items programmatically
        roleChoiceBox.setItems(FXCollections.observableArrayList("Patient", "Médecin", "Administrateur"));
    }

    public void setStage(Stage stage) {
        this.navigation = new Navigation(stage);
    }

    @FXML
    public void handleRegister() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        LocalDate dateNaissance = dateNaissanceField.getValue();
        String photo = photoField.getText();
        String telephone = telephoneField.getText();
        String roleSelection = roleChoiceBox.getValue();

        // Validate required fields
        if (email.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || roleSelection == null) {
            errorLabel.setText("Les champs email, mot de passe, nom, prénom et rôle sont obligatoires.");
            return;
        }

        // Map the role selection to the roles column format
        String roles;
        switch (roleSelection) {
            case "Patient":
                roles = "[]";
                break;
            case "Médecin":
                roles = "[\"ROLE_MEDCIN\"]";
                break;
            case "Administrateur":
                roles = "[\"ROLE_ADMIN\"]";
                break;
            default:
                roles = "[]"; // Default to patient
        }

        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setRole(roles);
        user.setDateNaissance(dateNaissance);
        user.setPhoto(photo.isEmpty() ? null : photo);
        user.setTel(telephone.isEmpty() ? null : telephone);
        user.setFailedLoginAttempts(0);
        user.setLocked(false);

        try {
            userService.addUser(user);
            navigation.navigateToLogin();
        } catch (SQLException e) {
            errorLabel.setText("Erreur lors de l'inscription : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goToLogin() {
        navigation.navigateToLogin();
    }
}
