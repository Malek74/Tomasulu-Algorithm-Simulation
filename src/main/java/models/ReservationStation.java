package models;

import controllers.mainController;

public class ReservationStation {
    public String tagName;
    boolean busy;
    operation operation;
    String qJ;
    String qK;
    int timeLeft;
    boolean isReady;

    public ReservationStation() {
        busy = false;
        timeLeft = -1;
        isReady = false;
        qJ = "";
        qK = "";
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

    public void clearReservationStation() {
        timeLeft = -1;
        busy = false;
        operation =null;
        qJ = "";
        qK = "";

    }

}
