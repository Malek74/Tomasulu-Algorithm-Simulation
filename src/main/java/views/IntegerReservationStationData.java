package views;

public class IntegerReservationStationData {
    private String tag;
    private String operation;
    private int VJ;
    private int VK;
    private String QJ;
    private String QK;
    private boolean busy;

    // Constructor
    public IntegerReservationStationData(String tag, String operation, int VJ, int VK, String QJ, String QK, boolean ready) {
        this.tag = tag;
        this.operation = operation;
        this.VJ = VJ;
        this.VK = VK;
        this.QJ = QJ;
        this.QK = QK;
        this.busy = ready;
    }

    // Getters and Setters
    public String getTag() {
        return tag;
    }

    public String getOperation() {
        return operation;
    }

    public float getVJ() {
        return VJ;
    }

    public float getVK() {
        return VK;
    }

    public String getQJ() {
        return QJ;
    }

    public String getQK() {
        return QK;
    }

    public boolean isBusy() {
        return busy;
    }
}
