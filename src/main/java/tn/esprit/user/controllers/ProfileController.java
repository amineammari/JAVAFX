package tn.esprit.user.controllers;

import tn.esprit.user.entities.Navigation;
import tn.esprit.user.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.user.services.Userservice;
import javafx.collections.FXCollections;

import java.sql.SQLException;

public class ProfileController {
    @FXML
    private Label titleLabel;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private DatePicker dateNaissanceField;

    @FXML
    private TextField telField;

    private User user;
    private Userservice userService = new Userservice();
    private Navigation navigation;

    public void setUser(User user) {
        this.user = user;
        initializeFields();
    }

    public void setStage(Stage stage) {
        this.navigation = new Navigation(stage);
    }

    private void initializeFields() {
        titleLabel.setText("Profil de " + user.getNom() + " " + user.getPrenom());
        emailField.setText(user.getEmail());
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        dateNaissanceField.setValue(user.getDateNaissance());
        telField.setText(user.getTel());
    }

    @FXML
    public void handleUpdate() {
        user.setEmail(emailField.getText());
        user.setNom(nomField.getText());
        user.setPrenom(prenomField.getText());
        user.setDateNaissance(dateNaissanceField.getValue());
        user.setTel(telField.getText());

        try {
            userService.updateUser(user);
            titleLabel.setText("Profil de " + user.getNom() + " " + user.getPrenom());
        } catch (SQLException e) {
            showError("Erreur de mise à jour", "Impossible de mettre à jour le profil.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer le compte");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                userService.deleteUser(user.getId());
                navigation.navigateToLogin();
            } catch (SQLException e) {
                showError("Erreur de suppression", "Impossible de supprimer le compte.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Déconnexion");
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            navigation.navigateToLogin();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
