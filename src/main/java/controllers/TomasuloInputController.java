package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TomasuloInputController {
    @FXML
    private TextField cacheHitTextField;
    @FXML
    private TextField cacheMissTextField;
    @FXML
    private TextField blockSizeTextField;
    @FXML
    private TextField cacheSizeTextField;
    // Add other TextField declarations as needed...

    public void initialize() {
        // Initialization logic, if needed
    }

    public void handleSubmit() {
        // Example: Retrieve data from the TextFields
        String cacheHitLatency = cacheHitTextField.getText();
        String cacheMissPenalty = cacheMissTextField.getText();
        String blockSize = blockSizeTextField.getText();
        String cacheSize = cacheSizeTextField.getText();

        // Process the inputs
        System.out.println("Cache Hit Latency: " + cacheHitLatency);
        System.out.println("Cache Miss Penalty: " + cacheMissPenalty);
        System.out.println("Block Size: " + blockSize);
        System.out.println("Cache Size: " + cacheSize);

        // Add logic to pass these values to your Tomasulo algorithm implementation
    }
}
