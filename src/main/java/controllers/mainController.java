package controllers;

import models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//array list of reservation station of
// hashmap for latencies

public class mainController {

    private Map<String, Integer> latencyMap = new HashMap<>();
    static LoadBuffer loadBuffer;
    static StoreBuffer storeBuffer;
    static RegisterFile registerFloat;
    static RegisterFile registerInt;
    static InstructionQueue instructionQueue;
    static int clock = 0;
    static Hashtable needsToWriteBack = new Hashtable();
    static FloatReservationStationBuffer floatReservationStationBuffer;
    static IntegerReservationStationBuffer integerReservationStationBuffer;
    static Memory memory;
    static ArrayList<ReservationStation> reservationStationsWriteBack;
    static ArrayList<StoreBufferEntry> storeBufferEntryWriteBack;
    static ArrayList<LoadBufferEntry> loadBufferEntryWriteBack;

    public static void main(String[] args) {

        // initialise all object
        initialiseObjects();
        // todo: load latencies
        Instruction currentInstruction;
        while(true){
            currentInstruction=instructionQueue.fetchInstruction();

            //check if the instruction can be issued
            if(issueInstruction(currentInstruction,currentInstruction.extractOperation())){
                instructionQueue.setIndex(instructionQueue.getIndex()+1);
            }

            //execute any instruction that is ready to execute
            floatReservationStationBuffer.executeInstruction();
            integerReservationStationBuffer.executeInstruction();
        }
    }

    public static void initialiseObjects() {
        // initialise reservation stations
        floatReservationStationBuffer = new FloatReservationStationBuffer(3, 3);
        integerReservationStationBuffer = new IntegerReservationStationBuffer(3, 3);

        // initialise load buffer
        loadBuffer = new LoadBuffer(3);

        // initialise store buffer
        storeBuffer = new StoreBuffer(2);

        // todo:initialise & load register file
        registerFloat = new RegisterFile("F", 31);
        registerInt = new RegisterFile("R", 31);
        loadRegisters("src/main/resources/floatRegisters.txt", registerFloat);
        loadRegisters("src/main/resources/intRegisters.txt", registerInt);

        // todo:initialise & load instruction queue
        instructionQueue = new InstructionQueue();
        instructionQueue.loadInstructionsFromFile("src\\main\\resources\\instructions.txt");

        // todo:initialise memory cache

    }

    public static void loadRegisters(String path, RegisterFile registerFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("=");

                registerFile.initializeRegister(parts[0], Double.parseDouble(parts[1]));

            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static boolean issueInstruction(Instruction instruction, String op) {

        // handle issue of float reservation buffers
        if (op.equals("ADD.D") || op.equals("ADD.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.ADD, registerFloat);
        }
        if (op.equals("SUB.D") || op.equals("SUB.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.SUB, registerFloat);
        }
        if (op.equals("MUL.D") || op.equals("MUL.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.MULT, registerFloat);
        }
        if (op.equals("DIV.D") || op.equals("DIV.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.DIV, registerFloat);
        }
        if (op.equals("SW") || op.equals("SD") || op.equals("S.S") || op.equals("S.D")) {
            return storeBuffer.issueInstruction(instruction, operation.STORE, registerFloat);
        }
        return false;
    }

    public static void writeBackInstructions() {
        //loop on reservation station to get one with highest number of dependencies

    }
    public void promptForLatencies(Scanner scanner) {

    }

    public int getNumberOfDependencies(ReservationStation station) {
        int count=0;

        //loop float registers to get number of dependencies
        for(int i=0;i<31;i++){
            Register register=registerFloat.getRegister("R"+i);

            if(register.getQi().equals(station.tagName)){
                count++;
            }
        }

        //loop integer registers to get number of dependencies
        for(int i=0;i<31;i++){
            Register register=registerInt.getRegister("F"+i);

            if(register.getQi().equals(station.tagName)){
                count++;
            }
        }

        //loop float reservation stations to get number of dependencies
        count+=floatReservationStationBuffer.getNumOfDependencies(station.getTagName())+integerReservationStationBuffer.getNumOfDependencies(station.getTagName());

        count+= storeBuffer.getNumOfDependencies(station.getTagName());
        return count;

    }
    static public void loadLatencies(){
        //todo:load latencies
    }

}
