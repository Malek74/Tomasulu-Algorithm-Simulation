package controllers;

import models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//array list of reservation station of
// hashmap for latencies

public class mainController {

    public static Hashtable<String, Integer> latencyMap = new Hashtable<>();
    public static LoadBuffer loadBuffer;
    public static StoreBuffer storeBuffer;
    public static RegisterFile registerFloat;
    public static RegisterFile registerInt;
    public static InstructionQueue instructionQueue;
    public static ArrayList<String> needsToWriteBack = new ArrayList();
    public static FloatReservationStationBuffer floatReservationStationBuffer;
    public static IntegerReservationStationBuffer integerReservationStationBuffer;
    public static MainMemory memory;
    public static Cache cache;
    public static ArrayList<ReservationStation> reservationStationsWriteBack;
    public static ArrayList<StoreBufferEntry> storeBufferEntryWriteBack;
    public static ArrayList<LoadBufferEntry> loadBufferEntryWriteBack;
    public static Hashtable<String, BranchInstruction> branchInstructionsBuffer;
    public static boolean noIssue;

    public static void main(String[] args) {

        // initialise all object
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

    public static void initialiseObjects(int floatMult,
            int floatAdd,
            int load,
            int store,
            int block,
            int cacheSize) {
        // initialise reservation stations
        floatReservationStationBuffer = new FloatReservationStationBuffer(floatMult, floatAdd);
        integerReservationStationBuffer = new IntegerReservationStationBuffer(floatMult, floatMult);

        // initialise load buffer
        loadBuffer = new LoadBuffer(load);

        // initialise store buffer
        storeBuffer = new StoreBuffer(store);

        // initialise & load register file
        registerFloat = new RegisterFile("F", 31);
        registerInt = new RegisterFile("R", 31);
        loadRegisters("src/main/resources/floatRegisters.txt", registerFloat);
        loadRegisters("src/main/resources/intRegisters.txt", registerInt);

        // initialise & load instruction queue
        instructionQueue = new InstructionQueue();
        instructionQueue.loadInstructionsFromFile("src\\main\\resources\\instructions.txt");

        // todo:initialise memory cache
        memory = new MainMemory(cacheSize);
        cache = new Cache(cacheSize, block);

        // initialise branch buffer
        branchInstructionsBuffer = new Hashtable<>();

    }


    private static void loadRegisters(String path, RegisterFile registerFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("=");
                System.out.println(parts[0] + " " + parts[1]);
                registerFile.initializeRegister(parts[0], Float.parseFloat(parts[1]));

            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean issueInstruction(Instruction instruction, String op) {

        // handle issue of float reservation buffers
        if (op.equals("ADD.D") || op.equals("ADD.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.ADD, registerFloat,
                    latencyMap.get("add"));
        }
        if (op.equals("DADDI")) {
            return integerReservationStationBuffer.issueInstruction(instruction, operation.DADDI, registerInt,
                    latencyMap.get("add"));
        }

        if (op.equals("DSUBI")) {
            return integerReservationStationBuffer.issueInstruction(instruction, operation.DSUBI, registerInt,
                    latencyMap.get("sub"));
        }
        if (op.equals("SUB.D") || op.equals("SUB.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.SUB, registerFloat,
                    latencyMap.get("sub"));
        }
        if (op.equals("MUL.D") || op.equals("MUL.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.MULT, registerFloat,
                    latencyMap.get("mult"));
        }
        if (op.equals("DIV.D") || op.equals("DIV.S")) {
            return floatReservationStationBuffer.issueInstruction(instruction, operation.DIV, registerFloat,
                    latencyMap.get("div"));
        }
        if (op.equals("SW") || op.equals("SD")) {
            return storeBuffer.issueInstruction(instruction, operation.STORE, registerInt, latencyMap.get("store"), op);
        }
        if (op.equals("S.S") || op.equals("S.D")) {
            return storeBuffer.issueInstruction(instruction, operation.STORE, registerFloat, latencyMap.get("store"),
                    op);

        }
        if (op.equals("LW") || op.equals("LD")) {

            return loadBuffer.issueInstructionLoad(instruction, operation.LOAD, registerInt,
                    op);
        }
        if (op.equals("L.S") || op.equals("L.D")) {
            return loadBuffer.issueInstructionLoad(instruction, operation.LOAD, registerFloat,
                    op);
        }
        if (op.equals("BEQ") || op.equals("BNE")) {
            BranchInstruction branchInstruction = new BranchInstruction(instruction, op, instructionQueue.getIndex());
            branchInstructionsBuffer.put("B" + instructionQueue.getIndex(), branchInstruction);
            noIssue = true;
            return branchInstruction.updateDueToIssue(instruction, op);

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
        System.out.println(needsToWriteBack);
        for (int i = 0; i < needsToWriteBack.size(); i++) {

            tagName = needsToWriteBack.get(i);
            comp = getNumberOfDependencies(tagName);

            // update tag to be written back if needed
            if (comp > numOFDependencies) {
                numOFDependencies = comp;
                tagToWriteBack = tagName;
            }

            // execute branch instruction if found
            if (tagName.contains("B")) {
                instructionQueue.setIndex(branchInstructionsBuffer.get(tagName).getDestination());
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
            // call the integer RS function
            integerReservationStationBuffer.writeBack(tagToWriteBack);
        }

        // check if the store buffer is ready to write back
        if (tagToWriteBack.contains("S")) {
            storeBuffer.writeEntry(tagToWriteBack);
        }

        if (tagToWriteBack.contains("L")) {
            loadBuffer.writeBackLoad(tagToWriteBack);
        }

        // remove the tag from the write back array
        needsToWriteBack.remove(tagToWriteBack);
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

    public static void execute() {

        // execute the reservation stations
        floatReservationStationBuffer.executeInstruction();
        integerReservationStationBuffer.executeInstruction();

        // execute the store buffer
        storeBuffer.executeStoreBuffer();

        // execute the load buffer
        loadBuffer.executeLoadBuffer();

        // execute branch instructions

        // update all branches that depend on tag
        for (String branch : branchInstructionsBuffer.keySet()) {
            branchInstructionsBuffer.get(branch).execute();
        }
    }

    public static void updateClockCycle() {

        // write back
        writeBack();

        // execute
        execute();

        // issue instruction
        Instruction instructionToIssue;
        if (instructionQueue.getIndex() < instructionQueue.size()) {

            instructionToIssue = instructionQueue.fetchInstruction();

            // if instruction can be issued increment the index
            if(!noIssue){
            if (  issueInstruction(instructionToIssue, instructionToIssue.extractOperation())) {
                System.out.println(instructionToIssue.getInstruction() + " issued");
                instructionQueue.setIndex(instructionQueue.getIndex() + 1);
            }}
        }

    }
}
