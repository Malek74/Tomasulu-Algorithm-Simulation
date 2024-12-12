package models;

public class LoadBufferEntry {

    private boolean busy; // Indicates if the entry is occupied
    private int address;// Holds the effective address
    private String tag;
    int timeLeft;

    // Constructor
    public LoadBufferEntry() {
        this.busy = false;
        this.address = -1; // -1 indicates no address stored
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    // Clear the entry
    public void clear() {
        this.busy = false;
        this.address = -1;
    }

    @Override
    public String toString() {
        return "[Busy=" + busy + ", Address=" + (address == -1 ? "None" : address) + "]";
    }
}
