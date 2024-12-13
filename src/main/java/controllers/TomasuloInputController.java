package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class TomasuloInputController {

    @FXML
    private TextField cacheHitTextField;
    @FXML
    private TextField cacheMissTextField;
    @FXML
    private TextField blockSizeTextField;
    @FXML
    private TextField cacheSizeTextField;
    @FXML
    private TextField storeLatency;
    @FXML
    private TextField loadLatency;
    @FXML
    private TextField addLatency;
    @FXML
    private TextField subLatency;
    @FXML
    private TextField multLatency;
    @FXML
    private TextField divLatency;
    @FXML
    private TextField floatAddRS;
    @FXML
    private TextField floatMultRS;
    @FXML
    private TextField intAddRS;
    @FXML
    private TextField intMultRS;
    @FXML
    private TextField loadRS;
    @FXML
    private TextField storeRS;

    @FXML
    private TextField branchLatency;

    @FXML
    private Button start_simulation;

    @FXML
    private void handleStartSimulation() {
        // Store values from the form into the mainController (or wherever necessary)
        mainController.latencyMap.putIfAbsent("load", Integer.valueOf(loadLatency.getText()));
        mainController.latencyMap.putIfAbsent("store", Integer.valueOf(storeLatency.getText()));
        mainController.latencyMap.putIfAbsent("add", Integer.valueOf(addLatency.getText()));
        mainController.latencyMap.putIfAbsent("sub", Integer.valueOf(subLatency.getText()));
        mainController.latencyMap.putIfAbsent("mult", Integer.valueOf(multLatency.getText()));
        mainController.latencyMap.putIfAbsent("div", Integer.valueOf(divLatency.getText()));
        mainController.latencyMap.putIfAbsent("hit", Integer.valueOf(cacheHitTextField.getText()));
        mainController.latencyMap.putIfAbsent("miss", Integer.valueOf(cacheMissTextField.getText()));
        mainController.latencyMap.putIfAbsent("branch",Integer.valueOf(branchLatency.getText()));

        System.out.println(mainController.latencyMap);

        mainController.initialiseObjects(
                Integer.valueOf(floatMultRS.getText()),
                Integer.valueOf(floatAddRS.getText()),
                Integer.valueOf(loadRS.getText()),
                Integer.valueOf(storeRS.getText()),
                Integer.valueOf(blockSizeTextField.getText()),
                Integer.valueOf(cacheSizeTextField.getText())

        );

        // Now load the new scene
        try {
            // Load the second scene (AnotherPage.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tomasulu/simulation.fxml"));
            System.out.println(getClass().getResource("/org/example/tomasulu/simulation.fxml"));

            Parent root = loader.load();

            // Create a new scene and set it in the primary stage
            Stage stage = (Stage) start_simulation.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Controller initialized.");
    }
}
