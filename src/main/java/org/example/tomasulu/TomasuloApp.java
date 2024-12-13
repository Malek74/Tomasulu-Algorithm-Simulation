package org.example.tomasulu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TomasuloApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TomasuloInputPage.fxml")); // Ensure this matches
                                                                                              // your FXML file name and// location
        Parent root = loader.load();
        primaryStage.setTitle("Tomasulo Algorithm Inputs");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
