package controllers;

import models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//array list of reservation station of
// hashmap for latencies

public class mainController {

    private Hashtable<String, Integer> latencyMap = new Hashtable<>();
    public static LoadBuffer loadBuffer;
    public static StoreBuffer storeBuffer;
    public static RegisterFile registerFloat;
    public static RegisterFile registerInt;
    public static InstructionQueue instructionQueue;
    public static int clock = 0;
    public static ArrayList<String> needsToWriteBack = new ArrayList();
    public static FloatReservationStationBuffer floatReservationStationBuffer;
    public static IntegerReservationStationBuffer integerReservationStationBuffer;
    public static Memory memory;
    public static ArrayList<ReservationStation> reservationStationsWriteBack;
    public static ArrayList<StoreBufferEntry> storeBufferEntryWriteBack;
    public static ArrayList<LoadBufferEntry> loadBufferEntryWriteBack;

    public static void main(String[] args) {

        // initialise all object
        initialiseObjects();
        // todo: load latencies
        Instruction currentInstruction;
        while (true) {
            currentInstruction = instructionQueue.fetchInstruction();

            // check if the instruction can be issued
            if (issueInstruction(currentInstruction, currentInstruction.extractOperation())) {
                instructionQueue.setIndex(instructionQueue.getIndex() + 1);
            }

            // execute any instruction that is ready to execute
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

    // decides which tag to write back in current cycle
    public static String writeBackDecision() {
        String tagName;
        int numOFDependencies = -1;
        int comp = 0;
        String tagToWriteBack = "None";

        // loop on writeback array to get one with the highest number of dependencies
        for (int i = 0; i < needsToWriteBack.size(); i++) {
            tagName = needsToWriteBack.get(i);
            comp = getNumberOfDependencies(tagName);

            // update tag to be written back if needed
            if (comp > numOFDependencies) {
                numOFDependencies = comp;
                tagToWriteBack = tagName;
            }

        }
        return tagToWriteBack;
    }

    public static void writeBack() {
        String tagToWriteBack = writeBackDecision();
        if (tagToWriteBack.equals("None")) {
            return;
        }

        // check if the float reservation station is ready to write back
        if (tagToWriteBack.contains("MF") || tagToWriteBack.contains("AF")) {
            floatReservationStationBuffer.writeBack(tagToWriteBack);
        }

        // check if the integer reservation station is ready to write back
        if (tagToWriteBack.contains("MR") || tagToWriteBack.contains("AR")) {
            // todo:call the integer RS function
            integerReservationStationBuffer.writeBack(tagToWriteBack);
        }

        // check if the store buffer is ready to write back
        if (tagToWriteBack.contains("S")) {
            // todo:call the store buffer function
        }

        if (tagToWriteBack.contains("L")) {
            // todo:call the load buffer function
        }

        // remove the tag from the write back array
        needsToWriteBack.remove(tagToWriteBack);
    }

    public void promptForLatencies(Scanner scanner) {

    }

    public static int getNumberOfDependencies(String tagName) {
        int count = 0;

        // get dependencies from registers
        count += registerFloat.countDependencies("F", tagName);
        count += registerInt.countDependencies("R", tagName);

        // get dependencies from reservation stations
        count += floatReservationStationBuffer.getNumOfDependencies(tagName)
                + integerReservationStationBuffer.getNumOfDependencies(tagName);

        // get dependencies from store buffer
        count += storeBuffer.getNumOfDependencies(tagName);

        if (tagName.contains("S")) {
            count++;
        }

        return count;

    }

    static public void loadLatencies() {
        // todo:load latencies
    }

}
