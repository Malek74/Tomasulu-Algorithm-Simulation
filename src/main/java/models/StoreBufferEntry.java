package models;

import controllers.TomasuloInputController;
import controllers.mainController;

public class StoreBufferEntry {
    private boolean busy; // Whether this buffer entry is in use
    private int address; // Memory address for the store operation
    private String tag;
    private MemoryBlock v; // Value to store (if ready)
    private String q; // Tag of the producing reservation station or functional unit (if not ready)
    int timeLeft;
    private boolean isReady;

    // Constructor: Initialize entry as empty
    public StoreBufferEntry(String tag) {
        this.tag = tag;
        this.busy = false;
        this.address = 0;
        this.v = new MemoryBlock(TomasuloInputController.blockSize); // NaN indicates value is not ready
        this.q = ""; // Null indicates no dependency

    }

    public StoreBufferEntry() {
        this.busy = false;
        this.address = 0;
        this.v = new MemoryBlock(TomasuloInputController.blockSize); // NaN indicates value is not ready
        this.q = ""; // Null indicates no dependency
    }

    public void execute() {
        if (busy && isReady) {
            timeLeft--;
            if (timeLeft == 0) {
                mainController.needsToWriteBack.add(tag);
            }
        }

    }

    // Getters and Setters
    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public float getV() {
        return v.translateWordToFloat();
    }

    public void setV(float value) {
        v.translateFloatToWord(value);
        this.q = ""; // If value is set, no dependency exists
    }

    public void setV(MemoryBlock value) {
        this.v = value;
        this.q = ""; // If value is set, no dependency exists
    }

    public void setV(double value) {
        v.translateDoubleToWord(value);
        this.q = ""; // If value is set, no dependency exists
    }

    public void setV(int value) {
        v.translateIntToWord(value);
        this.q = ""; // If value is set, no dependency exists
    }

    public void setV(long value) {
        v.translateLongToWord(value);
        this.q = ""; // If value is set, no dependency exists
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
        this.v = new MemoryBlock(TomasuloInputController.blockSize); // If dependency exists, value is not ready
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    // Clear this entry
    public void clear() {
        this.busy = false;
        this.address = 0;
        this.v = new MemoryBlock(TomasuloInputController.blockSize);
        this.q = "";
    }

    public void updateStoreBuffer(String tagName, MemoryBlock value) {
        if (q.equals(tagName)) {
            v = value; // Update the value if the tag matches
            q = ""; // Clear the dependency tag
        }
    }

    public void updateStoreBuffer(String tagName, double value) {
        if (q.equals(tagName)) {
            v.translateDoubleToWord(value); // Update the value if the tag matches
            q = ""; // Clear the dependency tag
        }
    }

    public void updateStoreBuffer(String tagName, int value) {
        if (q.equals(tagName)) {
            v.translateIntToWord(value); // Update the value if the tag matches
            q = ""; // Clear the dependency tag
        }
    }

    public void updateStoreBuffer(String tagName, long value) {
        if (q.equals(tagName)) {
            v.translateLongToWord(value); // Update the value if the tag matches
            q = ""; // Clear the dependency tag
        }
    }

    // String representation for debugging
    // @Override
    // public String toString() {
    // return "Busy: " + busy +
    // ", Address: " + address +
    // ", V: " + (Double.isNaN(v) ? "Not Ready" : v) +
    // ", Q: " + (q == null ? "None" : q);
    // }

    public void writeToMemory() {
        mainController.memory.writeBlockToMemory(address, v);
        this.clear();
    }

}
