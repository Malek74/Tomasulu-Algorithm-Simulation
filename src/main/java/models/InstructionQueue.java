package models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class InstructionQueue {
    private LinkedList<Instruction> instructionQueue = new LinkedList<>();
    private int index = 0;

    // Method to prompt user for latencies and load instructions
    public void initialize() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to the instruction file: ");
            String filePath = scanner.nextLine();
            filePath = filePath.replace("\"", "").trim();

            // Add the file existence check here
            File file = new File(filePath);
            if (file.exists()) {
                loadInstructionsFromFile(filePath); // Load instructions from the file
            } else {
                System.err.println("File not found at: " + filePath); // If the file doesn't exist, show an error
                                                                      // message
            }
        }

        System.out.println("Instruction Queue:\n");
        printInstructionQueue();
    }

    public String extractOperation(String instruction) {
        return instruction.split(" ")[0];
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    // Load instructions from a text file
    public void loadInstructionsFromFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Extract the operation from the instruction line
                String operation = extractOperation(line);
                // Add the operation to the uniqueInstructions set to avoid duplicates

                // Add the instruction to the queue with a placeholder latency (we'll update it
                // later)
                instructionQueue.add(new Instruction(line)); // Placeholder latency (-1) for now
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to get a valid latency input from the user
    private int getValidLatency(Scanner scanner) {
        int latency = -1;
        while (latency < 0) {
            try {
                latency = scanner.nextInt();
                if (latency < 0) {
                    System.out.print("Latency must be a positive integer. Try again: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input! Please enter a valid positive integer for latency: ");
                scanner.next(); // Clear the invalid input
            }
        }
        return latency;
    }

    public void printInstructionQueue() {
        for (Instruction instruction : instructionQueue) {
            System.out.println(instruction.getInstruction());
        }
    }

    public Instruction fetchInstruction() {
        if (instructionQueue.isEmpty()) {
            return null;
        }
        return instructionQueue.get(index);
    }

    public boolean isEmpty() {
        return instructionQueue.isEmpty();
    }

    public Instruction peek() {
        return instructionQueue.peek();
    }

    public int size(){
        return instructionQueue.size();
    }
    public static void main(String[] args) {
        InstructionQueue manager = new InstructionQueue();
        manager.initialize();
    }

}