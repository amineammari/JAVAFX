package tn.esprit.user.controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.user.entities.Navigation;
import tn.esprit.user.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.user.services.Userservice;

import java.sql.SQLException;
import java.util.List;

public class AdminController {
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    private User admin;
    private Userservice userService = new Userservice();
    private Navigation navigation;

    public void setAdmin(User admin) {
        this.admin = admin;
        initializeTable();
    }

    public void setStage(Stage stage) {
        this.navigation = new Navigation(stage);
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        try {
            List<User> users = userService.getAllUsers();
            userTable.getItems().addAll(users);
        } catch (SQLException e) {
            showError("Erreur de chargement", "Impossible de charger la liste des utilisateurs.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null && selectedUser.getId() != admin.getId()) {
            try {
                userService.deleteUser(selectedUser.getId());
                userTable.getItems().remove(selectedUser);
            } catch (SQLException e) {
                showError("Erreur de suppression", "Impossible de supprimer l'utilisateur.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleLogout() {
        navigation.navigateToLogin();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}