package models;

import controllers.TomasuloInputController;
import controllers.mainController;
import javafx.css.Size;

public class ReservationStation {
    public String tagName;
    boolean busy;
    operation operation;
    String qJ;
    String qK;
    int timeLeft;
    boolean isReady;
    MemoryBlock vJ;
    MemoryBlock vK;

    public ReservationStation() {
        busy = false;
        timeLeft = -1;
        isReady = false;
        qJ = "";
        qK = "";
        vJ = new MemoryBlock(TomasuloInputController.blockSize);
        vK = new MemoryBlock(TomasuloInputController.blockSize);
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void executeReservationStation() {
        if (isReady && busy) {
            timeLeft--;
            if (timeLeft == 0) {
                mainController.needsToWriteBack.add(tagName);
            }
        }

    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setOperation(operation operation) {
        System.out.println("Setting operation to " + operation);
        this.operation = operation;
    }

    public void setQJ(String qJ) {
        this.qJ = qJ;
    }

    public void setQK(String qK) {
        this.qK = qK;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean isBusy() {
        return busy;
    }

    public operation getOperation() {
        return operation;
    }

    public String getQJ() {
        return qJ;
    }

    public String getQK() {
        return qK;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setVJ(MemoryBlock vJ) {
        this.vJ = vJ;
    }

    public void setVK(MemoryBlock vK) {
        this.vK = vK;
    }

    public MemoryBlock getVJ() {
        return vJ;
    }

    public MemoryBlock getVK() {
        return vK;
    }

    public void clearReservationStation() {
        timeLeft = -1;
        busy = false;
        operation = null;
        qJ = "";
        qK = "";
        isReady = false;
        vJ = new MemoryBlock(TomasuloInputController.blockSize);
        vK = new MemoryBlock(TomasuloInputController.blockSize);

    }

}
