package models;

public class Instruction {
    private String instruction;
    private int latency;
    private String status;

    public Instruction(String instruction) {
        this.instruction = instruction;
        this.status = "Not Issued"; // Default status
    }

    // Getters
    public String getInstruction() {
        return instruction;
    }

    public int getLatency() {
        return latency;
    }

    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    public String extractOperation() {
        return instruction.split(" ")[0];
    }

    @Override
    public String toString() {
        return "Instruction: " + instruction + ", Latency: " + latency + ", Status: " + status;
    }
}
