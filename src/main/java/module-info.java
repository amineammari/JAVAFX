module tn.esprit.user {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jbcrypt;
    requires java.sql;

    opens tn.esprit.user to javafx.fxml;
    exports tn.esprit.user;
    exports tn.esprit.user.controllers;
    opens tn.esprit.user.controllers to javafx.fxml;
}