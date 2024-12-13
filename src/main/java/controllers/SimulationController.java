package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.*;
import views.CacheMemoryData;
import views.FloatReservationStationData;
import views.IntegerReservationStationData;

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
    private TableView<Register> floatRegistersTable;

    @FXML
    private TableView<Register> integerRegistersTable;

    @FXML
    private TableView<CacheMemoryData> memoryTable;

    @FXML
    private TableView<CacheMemoryData> cacheTable;

    private int clockCycle = 0;

    private ObservableList<FloatReservationStationData> floatMultRSData;
    private ObservableList<FloatReservationStationData> floatAddRSData;

    private ObservableList<IntegerReservationStationData> integerMultRSData;
    private ObservableList<IntegerReservationStationData> integerAddRSData;
    private ObservableList<LoadBufferEntry> loadBufferData;
    private ObservableList<StoreBufferEntry> storeBufferData;

    private ObservableList<Register> floatRegistersData;
    private ObservableList<Register> integerRegistersData;

    private ObservableList<CacheMemoryData> memoryContent;
    private ObservableList<CacheMemoryData> cacheDataContent;

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

        List<Register> floatData = getFloatRegistersData();
        List<Register> intData = getIntegerRegistersData();

        List<CacheMemoryData> memoryData = getMemoryData();
        List<CacheMemoryData> cacheData = getCacheData();

        // Clear the previous data from the ObservableLists
        floatMultRSData.clear();
        floatAddRSData.clear();
        integerMultRSData.clear();
        integerAddRSData.clear();
        loadBufferData.clear();
        storeBufferData.clear();
        floatRegistersData.clear();
        integerRegistersData.clear();
        memoryContent.clear();
        cacheDataContent.clear();

        // Add the new data to the ObservableLists
        floatMultRSData.addAll(multRSData);
        floatAddRSData.addAll(addRSData);
        integerMultRSData.addAll(multIntData);
        integerAddRSData.addAll(addIntData);
        loadBufferData.addAll(loadData);
        storeBufferData.addAll(storeData);
        floatRegistersData.addAll(floatData);
        integerRegistersData.addAll(intData);
        memoryContent.addAll(memoryData);
        cacheDataContent.addAll(cacheData);
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
        memoryContent = FXCollections.observableArrayList();
        cacheDataContent = FXCollections.observableArrayList();

        // Bind data to the tables
        bindFloatReservationStationTable(floatReservationStationsTable1, floatMultRSData);
        bindFloatReservationStationTable(floatReservationStationsTable2, floatAddRSData);
        bindIntReservationStationTable(integerReservationStationsTable1, integerMultRSData);
        bindIntReservationStationTable(integerReservationStationsTable2, integerAddRSData);
        bindLoadsBuffer(loadBuffersTable, loadBufferData);
        bindStoreBuffer(storeBuffersTable, storeBufferData);
        bindFloatRegistersTable(floatRegistersTable, floatRegistersData);
        bindIntegerRegistersTable(integerRegistersTable, integerRegistersData);
        bindMemoryTable(memoryTable, memoryContent);
        bindMemoryTable(cacheTable, cacheDataContent);
    }

    private void bindMemoryTable(TableView<CacheMemoryData> table, ObservableList<CacheMemoryData> data) {
        // Define columns for the table
        TableColumn<CacheMemoryData, String> tagColumn = new TableColumn<>("Address");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<CacheMemoryData, String> vjColumn = new TableColumn<>("Content");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        table.getColumns().addAll(tagColumn, vjColumn);

        table.setItems(data);

    }

    private void bindIntegerRegistersTable(TableView<Register> table, ObservableList<Register> data) {
        // Define columns for the table
        TableColumn<Register, String> tagColumn = new TableColumn<>("Name");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Register, String> vjColumn = new TableColumn<>("Value");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Register, String> operationColumn = new TableColumn<>("Qi");
        operationColumn.setCellValueFactory(new PropertyValueFactory<>("qi"));

        table.getColumns().addAll(tagColumn, vjColumn, operationColumn);

        // Set the data for the table
        table.setItems(data);
    }

    private void bindFloatRegistersTable(TableView<Register> table, ObservableList<Register> data) {
        // Define columns for the table
        TableColumn<Register, String> tagColumn = new TableColumn<>("Name");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Register, String> vjColumn = new TableColumn<>("Value");
        vjColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        TableColumn<Register, String> operationColumn = new TableColumn<>("Qi");
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

    public List<CacheMemoryData> getMemoryData() {
        List<CacheMemoryData> data = new ArrayList<>();
        for (int i = 0; i < mainController.memory.size(); i++) {
            data.add(new CacheMemoryData(i, mainController.memory.get(i)));
        }
        return data;
    }

    public List<CacheMemoryData> getCacheData() {
        List<CacheMemoryData> data = new ArrayList<>();
        for (int i = 0; i < mainController.cache.size(); i++) {
            data.add(new CacheMemoryData(i, mainController.cache.get(i)));
        }
        return data;
    }

    public List<Register> getIntegerRegistersData() {
        List<Register> data = new ArrayList<>();
        for (int i = 0; i < mainController.registerInt.size(); i++) {
            Register r = mainController.registerInt.getRegister("R" + i);
            data.add(new Register("R" + i, r.getValue(), r.getQi()));
        }
        return data;
    }

    public List<Register> getFloatRegistersData() {
        List<Register> data = new ArrayList<>();
        for (int i = 0; i < mainController.registerFloat.size(); i++) {
            Register r = mainController.registerFloat.getRegister("F" + i);
            data.add(new Register("F" + i, r.getValue(), r.getQi()));
        }
        return data;
    }

    public List<FloatReservationStationData> getMultReservationStationData() {
        List<FloatReservationStationData> data = new ArrayList<>();

        // For multiplication reservation stations
        for (FloatReservationStation rs : mainController.floatReservationStationBuffer.getFloatMultRS()) {
            String tagName = rs.getTagName() != null ? rs.getTagName() : "N/A";
            String operation = rs.getOperation() != null ? rs.getOperation().toString() : "N/A";
            float vj = rs.getVJ() != 0 ? rs.getVJ() : 0.0f;
            float vk = rs.getVK() != 0 ? rs.getVK() : 0.0f;
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
            float vj = rs.getVJ() != 0 ? rs.getVJ() : 0.0f;
            float vk = rs.getVK() != 0 ? rs.getVK() : 0.0f;
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
            int vj = rs.getVJ() != 0 ? rs.getVJ() : 0;
            int vk = rs.getVK() != 0 ? rs.getVK() : 0;
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
            int vj = rs.getVJ() != 0 ? rs.getVJ() : 0;
            int vk = rs.getVK() != 0 ? rs.getVK() : 0;
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
