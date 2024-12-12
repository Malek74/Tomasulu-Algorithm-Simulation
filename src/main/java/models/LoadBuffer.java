package models;


import java.util.ArrayList;

public class LoadBuffer {
	private ArrayList<LoadBufferEntry> buffer; // ArrayList of LoadBufferEntry

    // Constructor: Initialize buffer with the given size
    public LoadBuffer(int size) {
        buffer = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            buffer.add(new LoadBufferEntry());
        }
    }

    // Get the size of the load buffer
    public int getSize() {
        return buffer.size();
    }

    // Get a specific buffer entry
    public LoadBufferEntry getEntry(int index) {
        if (index >= 0 && index < buffer.size()) {
            return buffer.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid buffer index: " + index);
    }

    // New method: Set a buffer entry (mark busy and set address)
    public void setEntry(int index, int address) {
        if (index >= 0 && index < buffer.size()) {
            LoadBufferEntry entry = buffer.get(index);
            entry.setBusy(true);
            entry.setAddress(address);
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
            System.out.println("L" + (i + 1) + ": " + buffer.get(i));
        }
    }


    public static void main(String[] args) {
        // 1. Create a LoadBuffer with 3 entries
        System.out.println("Test Case 1: Create a LoadBuffer with 3 entries");
        LoadBuffer loadBuffer = new LoadBuffer(3);
        loadBuffer.printBuffer();
        System.out.println();

        // 2. Set values in the buffer
        System.out.println("Test Case 2: Set entries in the LoadBuffer");
        loadBuffer.setEntry(0, 100); // Set address 100 in the first entry
        loadBuffer.setEntry(1, 200); // Set address 200 in the second entry
        loadBuffer.setEntry(2, 300); // Set address 300 in the third entry
        loadBuffer.printBuffer();
        System.out.println();

        // 3. Clear a specific entry
        System.out.println("Test Case 3: Clear the first entry");
        loadBuffer.clearEntry(0); // Clear the first entry
        loadBuffer.printBuffer();
        System.out.println();

        // 4. Access and modify an entry directly
        System.out.println("Test Case 4: Modify the second entry directly");
        LoadBufferEntry entry = loadBuffer.getEntry(1); // Get the second entry
        entry.setAddress(500); // Update the address to 500
        entry.setBusy(false);  // Mark it as not busy
        loadBuffer.printBuffer();
        System.out.println();

        // 5. Check invalid index handling
        System.out.println("Test Case 5: Try accessing invalid indices");
        try {
            loadBuffer.setEntry(3, 400); // Invalid index
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

        try {
            loadBuffer.clearEntry(-1); // Invalid index
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
        System.out.println();

        // 6. Full reset of the buffer
        System.out.println("Test Case 6: Reset the entire buffer");
        for (int i = 0; i < loadBuffer.getSize(); i++) {
            loadBuffer.clearEntry(i);
        }
        loadBuffer.printBuffer();
        System.out.println();

        // 7. Reuse buffer by setting new entries
        System.out.println("Test Case 7: Reuse the buffer by setting new entries");
        loadBuffer.setEntry(0, 600); // Set address 600 in the first entry
        loadBuffer.setEntry(2, 700); // Set address 700 in the third entry
        loadBuffer.printBuffer();
    }
}
