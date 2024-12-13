package models;

import controllers.mainController;

public class BranchInstruction {
    private String tag;
    private String operation;
    private int destination;
    private float source1;
    private float source2;
    private String qJ;
    private String qK;
    private int timeLeft;
    private boolean isReady;

    public BranchInstruction(Instruction instruction, String operation, int number) {
        String instr = instruction.getInstruction();
        this.tag = "B" + number;
        this.operation = operation;
        this.destination = Integer.parseInt(instr.split(" ")[3]);
        this.timeLeft = mainController.latencyMap.get("branch");
    }

    public boolean updateDueToIssue(Instruction instruction, String operation) {
        String instr = instruction.getInstruction();
        String source1 = instr.split(" ")[1];
        String source2 = instr.split(" ")[2];
        Register source1Register;
        Register source2Register;

        if (source1.contains("R")) {
            source1Register = mainController.registerInt.getRegister(source1);
            source2Register = mainController.registerInt.getRegister(source2);
        } else {
            source1Register = mainController.registerFloat.getRegister(source1);
            source2Register = mainController.registerFloat.getRegister(source2);
        }

        if (source1Register.getQi() == null || source1Register.getQi().equals("")) {
            this.source1 = source1Register.getValue();
        } else {
            this.qJ = source1Register.getQi();
        }

        if (source2Register.getQi() == null || source2Register.getQi().equals("")) {
            this.source2 = source2Register.getValue();
        } else {
            this.qK = source2Register.getQi();
        }
        return qJ.equals("") && qK.equals("");
    }

    public void updateDueToWriteBack(String tag, float value) {
        if (tag.equals(qJ)) {
            source1 = value;
            qJ = "";
        }
        if (tag.equals(qK)) {
            source2 = value;
            qK = "";
        }

        if (qJ.equals("") && qK.equals("")) {
            isReady = true;
        }
    }

    public void execute() {
        if (isReady) {
            timeLeft--;
            if (timeLeft == 0) {
                mainController.instructionQueue.setIndex(destination);
            }
        }

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public float getSource1() {
        return source1;
    }

    public void setSource1(float source1) {
        this.source1 = source1;
    }

    public float getSource2() {
        return source2;
    }

    public void setSource2(float source2) {
        this.source2 = source2;
    }

    public String getQJ() {
        return qJ;
    }

    public void setQJ(String qJ) {
        this.qJ = qJ;
    }

    public String getQK() {
        return qK;
    }

    public void setQK(String qK) {
        this.qK = qK;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
}
