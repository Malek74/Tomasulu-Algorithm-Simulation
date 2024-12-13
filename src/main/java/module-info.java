module org.example.tomasulu {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // Required for property bindings

    // Additional libraries used in the project
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    // Open packages for reflection to JavaFX modules
    opens org.example.tomasulu to javafx.fxml, javafx.base;
    opens controllers to javafx.fxml;
    opens models to javafx.base, javafx.fxml; // Open models to both javafx.base and javafx.fxml
    opens views to javafx.base, javafx.fxml;

    // Export packages
    exports org.example.tomasulu;
    exports controllers;
}
