module org.example.tomasulu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens org.example.tomasulu to javafx.fxml;
    exports org.example.tomasulu;
}