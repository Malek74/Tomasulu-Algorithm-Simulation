package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.*;
import views.FloatReservationStationData;
import views.IntegerReservationStationData;
import views.floatRegisterData;
import views.intRegisterData;

import java.util.ArrayList;
import java.util.List;

public class SimulationController {

    @FXML
    private Text clockCycleText;

    @FXML
    private Button nextCycleButton;

    @FXML
    private TableView<FloatReservationStationData> floatReservationStationsTable1;

    @FXML
    private TableView<FloatReservationStationData> floatReservationStationsTable2;

    @FXML
    private TableView<IntegerReservationStationData> integerReservationStationsTable1;

    @FXML
    private TableView<IntegerReservationStationData> integerReservationStationsTable2;

    @FXML
    private TableView<LoadBufferEntry> loadBuffersTable;

    @FXML
    private TableView<StoreBufferEntry> storeBuffersTable;

    @FXML
    private TableView<floatRegisterData> floatRegistersTable;

    @FXML
    private TableView<intRegisterData> integerRegistersTable;

    private int clockCycle = 0;

    private ObservableList<FloatReservationStationData> floatMultRSData;
    private ObservableList<FloatReservationStationData> floatAddRSData;

    private ObservableList<IntegerReservationStationData> integerMultRSData;
    private ObservableList<IntegerReservationStationData> integerAddRSData;
    private ObservableList<LoadBufferEntry> loadBufferData;
    private ObservableList<StoreBufferEntry> storeBufferData;

    private ObservableList<floatRegisterData> floatRegistersData;
    private ObservableList<intRegisterData> integerRegistersData;

    @FXML
    public void initialize() {
        // Set initial clock cycle text
        clockCycleText.setText("Clock Cycle: " + clockCycle);

        // Set action for nextCycleButton
        nextCycleButton.setOnAction(event -> updateClockCycle());

        // Initialize table views with data or placeholders as needed
        initializeTables();
    }

    private void updateClockCycle() {
        // Increment the clock cycle and update the text
        clockCycle++;
        clockCycleText.setText("Clock Cycle: " + clockCycle);
        mainController.updateClockCycle();

        // Update the tables' data with each cycle if necessary
        // Get updated data for the reservation stations
        List<FloatReservationStationData> multRSData = getMultReservationStationData();
        List<FloatReservationStationData> addRSData = getAddReservationStationData();

        List<IntegerReservationStationData> multIntData = getIntMultReservationData();
        List<IntegerReservationStationData> addIntData = getIntAddReservationData();

        List<LoadBufferEntry> loadData = getLoadBufferEntry();
        List<StoreBufferEntry> storeData = mainController.storeBuffer.getBuffer();

        List<floatRegisterData> floatData = getFloatRegistersData();
        List<intRegisterData> intData = getIntegerRegistersData();

        // Clear the previous data from the ObservableLists
        floatMultRSData.clear();
        floatAddRSData.clear();
        integerMultRSData.clear();
        integerAddRSData.clear();
        loadBufferData.clear();
        storeBufferData.clear();
        floatRegistersData.clear();
        integerRegistersData.clear();

        // Add the new data to the ObservableLists
        floatMultRSData.addAll(multRSData);
        floatAddRSData.addAll(addRSData);
        integerMultRSData.addAll(multIntData);
        integerAddRSData.addAll(addIntData);
        loadBufferData.addAll(loadData);
        storeBufferData.addAll(storeData);
        floatRegistersData.addAll(floatData);
        integerRegistersData.addAll(intData);
    }

    @FXML
    private void initializeTables() {
        // Initialize ObservableLists for multiplication and addition reservation
        // stations
        floatMultRSData = FXCollections.observableArrayList();
        floatAddRSData = FXCollections.observableArrayList();
        integerMultRSData = FXCollections.observableArrayList();
        integerAddRSData = FXCollections.observableArrayList();
        loadBufferData = FXCollections.observableArrayList();
        storeBufferData = FXCollections.observableArrayList();
        floatRegistersData = FXCollections.observableArrayList();
        integerRegistersData = FXCollections.observableArrayList();

        // Bind data to the tables
        bindFloatReservationStationTable(floatReservationStationsTable1, floatMultRSData);
        bindFloatReservationStationTable(floatReservationStationsTable2, floatAddRSData);
        bindIntReservationStationTable(integerReservationStationsTable1, integerMultRSData);
        bindIntReservationStationTable(integerReservationStationsTable2, integerAddRSData);
        bindLoadsBuffer(loadBuffersTable, loadBufferData);
        bindStoreBuffer(storeBuffersTable, storeBufferData);
        bindFloatRegistersTable(floatRegistersTable, floatRegistersData);
        bindIntegerRegistersTable(integerRegistersTable, integerRegistersData);
    }

    private void bindIntegerRegistersTable(TableView<intRegisterData> table, ObservableList<intRegisterData> data) {
        // Define columns for the table
        TableColumn<intRegisterData, String> tagColumn = new TableColumn<>("Name");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<intRegisterData, String> vjColumn = new TableColumn<>("Value");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<intRegisterData, String> operationColumn = new TableColumn<>("Qi");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("qi"));

        table.getColumns().addAll(tagColumn, vjColumn, operationColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindFloatRegistersTable(TableView<floatRegisterData> table, ObservableList<floatRegisterData> data) {
        // Define columns for the table
        TableColumn<floatRegisterData, String> tagColumn = new TableColumn<>("Name");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<floatRegisterData, String> vjColumn = new TableColumn<>("Value");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<floatRegisterData, String> operationColumn = new TableColumn<>("Qi");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("qi"));

        table.getColumns().addAll(tagColumn, vjColumn, operationColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindFloatReservationStationTable(TableView<FloatReservationStationData> table,
            ObservableList<FloatReservationStationData> data) {

        // Define columns for the table
        TableColumn<FloatReservationStationData, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<FloatReservationStationData, String> operationColumn = new TableColumn<>("Operation");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("operation"));

        TableColumn<FloatReservationStationData, String> vjColumn = new TableColumn<>("VJ");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("VJ"));

        TableColumn<FloatReservationStationData, String> vkColumn = new TableColumn<>("VK");
        vkColumn.setCellValueFactory(new PropertyValueFactory<>("VK"));

        TableColumn<FloatReservationStationData, String> qjColumn = new TableColumn<>("QJ");
        qjColumn.setCellValueFactory(new PropertyValueFactory<>("QJ"));

        TableColumn<FloatReservationStationData, String> qkColumn = new TableColumn<>("QK");
        qkColumn.setCellValueFactory(new PropertyValueFactory<>("QK"));

        // Add the columns to the table
        table.getColumns().addAll(tagColumn, operationColumn, vjColumn, vkColumn, qjColumn, qkColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindIntReservationStationTable(TableView<IntegerReservationStationData> table,
            ObservableList<IntegerReservationStationData> data) {

        // Define columns for the table
        TableColumn<IntegerReservationStationData, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<IntegerReservationStationData, String> operationColumn = new TableColumn<>("Operation");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("operation"));

        TableColumn<IntegerReservationStationData, String> vjColumn = new TableColumn<>("VJ");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("VJ"));

        TableColumn<IntegerReservationStationData, String> vkColumn = new TableColumn<>("VK");
        vkColumn.setCellValueFactory(new PropertyValueFactory<>("VK"));

        TableColumn<IntegerReservationStationData, String> qjColumn = new TableColumn<>("QJ");
        qjColumn.setCellValueFactory(new PropertyValueFactory<>("QJ"));

        TableColumn<IntegerReservationStationData, String> qkColumn = new TableColumn<>("QK");
        qkColumn.setCellValueFactory(new PropertyValueFactory<>("QK"));

        // Add the columns to the table
        table.getColumns().addAll(tagColumn, operationColumn, vjColumn, vkColumn, qjColumn, qkColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindLoadsBuffer(TableView<LoadBufferEntry> table, ObservableList<LoadBufferEntry> data) {

        // Define columns for the table
        TableColumn<LoadBufferEntry, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<LoadBufferEntry, String> busyColumn = new TableColumn<>("Busy");
        busyColumn.setCellValueFactory(new PropertyValueFactory<>("busy"));

        TableColumn<LoadBufferEntry, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        // Add the columns to the table
        table.getColumns().addAll(tagColumn, busyColumn, addressColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindStoreBuffer(TableView<StoreBufferEntry> table, ObservableList<StoreBufferEntry> data) {

        // Define columns for the table
        TableColumn<StoreBufferEntry, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<StoreBufferEntry, String> busyColumn = new TableColumn<>("Busy");
        busyColumn.setCellValueFactory(new PropertyValueFactory<>("busy"));

        TableColumn<StoreBufferEntry, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<StoreBufferEntry, String> vColumn = new TableColumn<>("V");
        vColumn.setCellValueFactory(new PropertyValueFactory<>("v"));

        TableColumn<StoreBufferEntry, String> qColumn = new TableColumn<>("Q");
        qColumn.setCellValueFactory(new PropertyValueFactory<>("q"));
        // Add the columns to the table
        table.getColumns().addAll(tagColumn, busyColumn, addressColumn, vColumn, qColumn);

        // Set the data for the table
        table.setItems(data);

    }

    public List<intRegisterData> getIntegerRegistersData() {
        List<intRegisterData> data = new ArrayList<>();
        for (int i = 0; i < mainController.registerInt.size(); i++) {
            Register r = mainController.registerInt.getRegister("R" + i);
            data.add(new intRegisterData("R" + i, r.getMemoryBlock().translateWordToLong(), r.getQi()));
        }
        return data;
    }

    public List<floatRegisterData> getFloatRegistersData() {
        List<floatRegisterData> data = new ArrayList<>();
        for (int i = 0; i < mainController.registerFloat.size(); i++) {
            Register r = mainController.registerFloat.getRegister("F" + i);
            data.add(new floatRegisterData("F" + i, r.getMemoryBlock().translateWordToDouble(), r.getQi()));
        }
        return data;
    }

    public List<FloatReservationStationData> getMultReservationStationData() {
        List<FloatReservationStationData> data = new ArrayList<>();

        // For multiplication reservation stations
        for (FloatReservationStation rs : mainController.floatReservationStationBuffer.getFloatMultRS()) {
            String tagName = rs.getTagName() != null ? rs.getTagName() : "N/A";
            String operation = rs.getOperation() != null ? rs.getOperation().toString() : "N/A";
            double vj = rs.getVJ().translateWordToDouble() != 0 ? rs.getVJ().translateWordToDouble() : 0.0f;
            double vk = rs.getVK().translateWordToDouble() != 0 ? rs.getVK().translateWordToDouble() : 0.0f;
            String qj = rs.getQJ() != null ? rs.getQJ() : "N/A";
            String qk = rs.getQK() != null ? rs.getQK() : "N/A";
            boolean ready = rs.isBusy();

            data.add(new FloatReservationStationData(tagName, operation, vj, vk, qj, qk, ready));
        }

        return data;
    }

    public List<FloatReservationStationData> getAddReservationStationData() {
        List<FloatReservationStationData> data = new ArrayList<>();

        for (FloatReservationStation rs : mainController.floatReservationStationBuffer.getFloatAddRS()) {
            String tagName = rs.getTagName() != null ? rs.getTagName() : "N/A";
            String operation = rs.getOperation() != null ? rs.getOperation().toString() : "N/A";
            double vj = rs.getVJ().translateWordToDouble() != 0 ? rs.getVJ().translateWordToDouble() : 0.0f;
            double vk = rs.getVK().translateWordToDouble() != 0 ? rs.getVK().translateWordToDouble() : 0.0f;
            String qj = rs.getQJ() != null ? rs.getQJ() : "N/A";
            String qk = rs.getQK() != null ? rs.getQK() : "N/A";
            boolean ready = rs.isBusy();

            data.add(new FloatReservationStationData(tagName, operation, vj, vk, qj, qk, ready));
        }
        return data;
    }

    public List<IntegerReservationStationData> getIntMultReservationData() {
        List<IntegerReservationStationData> data = new ArrayList<>();
        for (IntegerReservationStation rs : mainController.integerReservationStationBuffer.getIntMultRS()) {
            String tagName = rs.getTagName() != null ? rs.getTagName() : "N/A";
            String operation = rs.getOperation() != null ? rs.getOperation().toString() : "N/A";
            long vj = rs.getVJ().translateWordToLong() != 0 ? rs.getVJ().translateWordToLong() : 0;
            long vk = rs.getVK().translateWordToLong() != 0 ? rs.getVK().translateWordToLong() : 0;
            String qj = rs.getQJ() != null ? rs.getQJ() : "N/A";
            String qk = rs.getQK() != null ? rs.getQK() : "N/A";
            boolean ready = rs.isBusy();

            data.add(new IntegerReservationStationData(tagName, operation, vj, vk, qj, qk, ready));
        }
        return data;
    }

    public List<IntegerReservationStationData> getIntAddReservationData() {
        List<IntegerReservationStationData> data = new ArrayList<>();
        for (IntegerReservationStation rs : mainController.integerReservationStationBuffer.getIntAddRS()) {
            String tagName = rs.getTagName() != null ? rs.getTagName() : "N/A";
            String operation = rs.getOperation() != null ? rs.getOperation().toString() : "N/A";
            long vj = rs.getVJ().translateWordToLong() != 0 ? rs.getVJ().translateWordToLong() : 0;
            long vk = rs.getVK().translateWordToLong() != 0 ? rs.getVK().translateWordToLong() : 0;
            String qj = rs.getQJ() != null ? rs.getQJ() : "N/A";
            String qk = rs.getQK() != null ? rs.getQK() : "N/A";
            boolean ready = rs.isBusy();
            data.add(new IntegerReservationStationData(tagName, operation, vj, vk, qj, qk, ready));

        }
        return data;
    }

    public List<LoadBufferEntry> getLoadBufferEntry() {
        List<LoadBufferEntry> data = new ArrayList<>();

        for (LoadBufferEntry loadBufferEntry : mainController.loadBuffer.getBuffer()) {
            data.add(loadBufferEntry);
        }
        return data;
    }
}
