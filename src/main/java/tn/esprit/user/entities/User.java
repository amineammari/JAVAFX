package tn.esprit.user.entities;

import java.time.LocalDate;

public class User {
    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String photo;
    private String role; // This will store the JSON-like string (e.g., ["ROLE_ADMIN"])
    private int failedLoginAttempts;
    private boolean isLocked;
    private String tel;

    public User() {}

    public User(String email, String password, String nom, String prenom, String role) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.failedLoginAttempts = 0;
        this.isLocked = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean locked) { this.isLocked = locked; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
}