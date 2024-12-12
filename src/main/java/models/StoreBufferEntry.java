package models;

import controllers.mainController;

public class StoreBufferEntry {
    private boolean busy; // Whether this buffer entry is in use
    private int address; // Memory address for the store operation
    private String tag;
    private double v; // Value to store (if ready)
    private String q; // Tag of the producing reservation station or functional unit (if not ready)
    int timeLeft;

    // Constructor: Initialize entry as empty
    public StoreBufferEntry() {
        this.busy = false;
        this.address = 0;
        this.v = Double.NaN; // NaN indicates value is not ready
        this.q = null; // Null indicates no dependency
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

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
        this.q = null; // If value is set, no dependency exists
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
        this.v = Double.NaN; // If dependency exists, value is not ready
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
        this.v = Double.NaN;
        this.q = null;
    }

    public void updateStoreBuffer(String tagName, double value) {
        if (q.equals(tagName)) {
            v = value; // Update the value if the tag matches
            q = ""; // Clear the dependency tag
        }
    }

    // String representation for debugging
    @Override
    public String toString() {
        return "Busy: " + busy +
                ", Address: " + address +
                ", V: " + (Double.isNaN(v) ? "Not Ready" : v) +
                ", Q: " + (q == null ? "None" : q);
    }

    public void writeToMemory() {
       mainController.memory.store(address, v);
        this.clear();
    }


}
