package models;


import java.util.ArrayList;

public class StoreBuffer {
    private ArrayList<StoreBufferEntry> buffer;

    // Constructor: Initialize the buffer with a given size
    public StoreBuffer(int size) {
        buffer = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            buffer.add(new StoreBufferEntry());
        }
    }

    // Get the size of the store buffer
    public int getSize() {
        return buffer.size();
    }

    // Get a specific buffer entry
    public StoreBufferEntry getEntry(int index) {
        if (index >= 0 && index < buffer.size()) {
            return buffer.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid buffer index: " + index);
    }

    // Set an entry (mark busy, set address, and manage dependencies)
    public void setEntry(int index, int address, double v, String q) {
        if (index >= 0 && index < buffer.size()) {
            StoreBufferEntry entry = buffer.get(index);
            entry.setBusy(true);
            entry.setAddress(address);
            if (!Double.isNaN(v)) {
                entry.setV(v); // Set value if it's ready
            } else {
                entry.setQ(q); // Otherwise, set the dependency tag
            }
        } else {
            throw new IndexOutOfBoundsException("Invalid buffer index: " + index);
        }
    }

    // Clear a buffer entry
    public void clearEntry(int index) {
        if (index >= 0 && index < buffer.size()) {
            buffer.get(index).clear();
        } else {
            throw new IndexOutOfBoundsException("Invalid buffer index: " + index);
        }
    }

    // Print all entries in the buffer
    public void printBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            System.out.println("S" + (i + 1) + ": " + buffer.get(i));
        }
    }

    public boolean issueInstruction(Instruction instruction, operation type, RegisterFile registerFile) {
        // Parse the instruction
        String[] operands = instruction.getInstruction().split(" ");
        String sourceRegisterName = operands[1]; // e.g., "F4"
        int effectiveAddress = Integer.parseInt(operands[2]); // Direct address e.g., "100"

        // Find an available store buffer entry
        for (int i = 0; i < buffer.size(); i++) {
            StoreBufferEntry entry = buffer.get(i);
            if (!entry.isBusy()) {


                // Handle the source register
                Register sourceRegister = registerFile.getRegister(sourceRegisterName);
                double valueToStore;

                String producingTag;

                if (sourceRegister.getQi().equals("0")) {
                    valueToStore = sourceRegister.getValue(); // Value is ready
                    producingTag = null; // No dependency

                } else {
                    valueToStore = Double.NaN;
                    producingTag = sourceRegister.getQi();
                }
                // Set the store buffer entry
                setEntry(i, effectiveAddress, valueToStore, producingTag);
                // Update the instruction's status
                instruction.setStatus("Issued to StoreBuffer");
                return true; // Successfully issued
            }
        }
        return false; // No available store buffer entry
    }

    public int getNumOfDependencies(String tag){
        StoreBufferEntry entry=new StoreBufferEntry();
        int count=0;

        for(int i=0;i<buffer.size();i++){
            entry=buffer.get(i);
            if(entry.getQ().equals(tag)){
                count++;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        // 1. Create a StoreBuffer with 3 entries
        System.out.println("Test Case 1: Create a StoreBuffer with 3 entries");
        StoreBuffer storeBuffer = new StoreBuffer(3);
        storeBuffer.printBuffer();
        System.out.println();

        // 2. Set entries with ready values
        System.out.println("Test Case 2: Set entries with ready values");
        storeBuffer.setEntry(0, 100, 4.5, null); // Address 100, Value 4.5, no dependency
        storeBuffer.setEntry(1, 200, 7.2, null); // Address 200, Value 7.2, no dependency
        storeBuffer.printBuffer();
        System.out.println();

        // 3. Set entries with dependencies
        System.out.println("Test Case 3: Set entries with dependencies");
        storeBuffer.setEntry(2, 300, Double.NaN, "M1"); // Address 300, dependency on M1
        storeBuffer.printBuffer();
        System.out.println();

        // 4. Clear a specific entry
        System.out.println("Test Case 4: Clear the second entry");
        storeBuffer.clearEntry(1);
        storeBuffer.printBuffer();
        System.out.println();

        // 5. Modify a dependent entry when its value becomes ready
        System.out.println("Test Case 5: Resolve a dependency by setting its value");
        StoreBufferEntry entry = storeBuffer.getEntry(2);
        entry.setV(9.8); // Dependency resolved, set value 9.8
        storeBuffer.printBuffer();
        System.out.println();

        // 6. Handle invalid indices
        System.out.println("Test Case 6: Handle invalid indices");
        try {
            storeBuffer.setEntry(3, 400, 5.0, null); // Invalid index
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
    }
}

