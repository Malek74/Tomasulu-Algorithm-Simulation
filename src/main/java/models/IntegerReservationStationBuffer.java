package models;

import controllers.mainController;

public class IntegerReservationStationBuffer {
    static IntegerReservationStation[] intMultRS;
    static IntegerReservationStation[] intAddRS;

    public IntegerReservationStationBuffer(int multSize, int addSize) {
        intMultRS = new IntegerReservationStation[multSize];
        intAddRS = new IntegerReservationStation[addSize];

        for (int i = 0; i < multSize; i++) {
            intMultRS[i] = new IntegerReservationStation();
            intMultRS[i].setTagName("MR" + i);
        }

        for (int i = 0; i < addSize; i++) {
            intAddRS[i] = new IntegerReservationStation();
            intAddRS[i].setTagName("AR" + i);
        }
    }

    public void writeBack(String tag) {

        int index = Integer.parseInt(tag.charAt(2) + "");
        int value = 0;

        // get the value of the tag
        if (tag.contains("MR")) {
            String op = intMultRS[index].operation.toString();
            if (op.equals("MULT")) {
                value = intMultRS[index].getVJ() * intMultRS[index].getVK();
            } else if (op.equals("DIV")) {
                value = intMultRS[index].getVJ() / intMultRS[index].getVK();
            }
            intMultRS[index].clearReservationStation();

        } else if (tag.contains("AR")) {
            String op = intAddRS[index].operation.toString();
            if (op.equals("ADD")) {
                value = intAddRS[index].getVJ() + intAddRS[index].getVK();
            } else if (op.equals("SUB")) {
                value = intAddRS[index].getVJ() - intAddRS[index].getVK();
            } else if (op.equals("DADDI")) {
                value = intAddRS[index].getVJ() + intAddRS[index].getVK();
            } else if (op.equals("DSUBI")) {
                value = intAddRS[index].getVJ() - intAddRS[index].getVK();
            }

            intAddRS[index].clearReservationStation();
        }

        updateReservationStation(tag, value);

        // update all store buffer entries that depend on this tag
        mainController.storeBuffer.updateStoreBuffer(tag, value);

        // todo:update all register files that depend on this tag
        mainController.registerFloat.updateRegister(tag, (float) (value), "F");
        mainController.registerInt.updateRegister(tag, value, "R");

        // update all branches that depend on tag
        for (String branch : mainController.branchInstructionsBuffer.keySet()) {
            mainController.branchInstructionsBuffer.get(branch).updateDueToWriteBack(tag, value);
        }

    }

    public void updateReservationStation(String tag, int value) {

        // update all reservation stations that depend on this tag
        for (int i = 0; i < intAddRS.length; i++) {
            intAddRS[i].updateReservationStation(tag, value);
        }

        for (int i = 0; i < intMultRS.length; i++) {
            intMultRS[i].updateReservationStation(tag, value);
        }
    }

    public boolean issueInstruction(Instruction instruction, operation type, RegisterFile registerFile, int time) {

        // split the instruction
        String[] operands = instruction.getInstruction().split(" ");

        Register register;

        switch (type) {
            case ADD:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {

                        intAddRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;
                        intAddRS[i].setBusy(true);

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvJ((int) register.getValue());
                            intAddRS[i].setReady(true);
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvK((int) register.getValue());
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intAddRS[i].getTagName());

                        // todo:add the logic to issue the instruction in the reservation station
                        return true;
                    }
                }
                return false;
            case DADDI:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {

                        intAddRS[i].setBusy(true);
                        intAddRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;
                        // set the destination register

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvJ((int) register.getValue());
                            intAddRS[i].setReady(true);
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }

                        // check second operand in register file

                        // todo:el default ehh neseet
                        intAddRS[i].setvK(Integer.parseInt(operands[3]));

                        registerFile.updateRegisterDuetoIssue(operands[1], intAddRS[i].getTagName());
                        return true;
                    }
                    // todo:add the logic to issue the instruction in the reservation station

                }
                return false;
            case DSUBI:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {
                        intAddRS[i].setBusy(true);

                        intAddRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvJ((int) register.getValue());
                            intAddRS[i].setReady(true);
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }

                        // check second operand in register file

                        // todo:el default ehh neseet
                        intAddRS[i].setvK(Integer.parseInt(operands[3]));

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intAddRS[i].getTagName());

                        return true;
                    }
                }
                return false;

            case MULT:
                for (int i = 0; i < intMultRS.length; i++) {
                    if (!intMultRS[i].isBusy()) {
                        intMultRS[i].setBusy(true);
                        intMultRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;

                        // check first operand in register file
                        register = registerFile.getRegister(operands[1]);

                        if (register.getQi().equals("0")) {
                            intMultRS[i].setvJ((int) register.getValue());
                            intMultRS[i].setReady(true);
                        } else {
                            intMultRS[i].setQJ(register.getQi());
                            intMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("")) {
                            intMultRS[i].setvK((int) register.getValue());
                        } else {
                            intMultRS[i].setQJ(register.getQi());
                            intMultRS[i].setReady(false);
                        }
                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intMultRS[i].getTagName());

                        return true;
                    }
                }
                return false;
            case SUB:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {

                        intAddRS[i].setBusy(true);
                        intAddRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;

                        // check first operand in register file
                        register = registerFile.getRegister(operands[1]);

                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvJ((int) register.getValue());
                            intAddRS[i].setReady(true);
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            intAddRS[i].setvK((int) register.getValue());
                            intAddRS[i].setReady(true);
                        } else {
                            intAddRS[i].setQJ(register.getQi());
                            intAddRS[i].setReady(false);
                        }
                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intAddRS[i].getTagName());

                        return true;
                    }
                }
                return false;
            case DIV:
                for (int i = 0; i < intMultRS.length; i++) {
                    if (!intMultRS[i].isBusy()) {
                        intMultRS[i].setBusy(true);
                        intMultRS[i].setOperation(type);
                        intAddRS[i].timeLeft = time;

                        // check first operand in register file
                        register = registerFile.getRegister(operands[1]);

                        if (register.getQi().equals("0")) {
                            intMultRS[i].setvJ((int) register.getValue());
                            intMultRS[i].setReady(true);
                        } else {
                            intMultRS[i].setQJ(register.getQi());
                            intMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            intMultRS[i].setvK((int) register.getValue());
                            intMultRS[i].setReady(true);
                        } else {
                            intMultRS[i].setQJ(register.getQi());
                            intMultRS[i].setReady(false);
                        }
                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intMultRS[i].getTagName());

                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public void executeInstruction() {
        for (int i = 0; i < intMultRS.length; i++) {
            intMultRS[i].executeReservationStation();
        }
        for (int i = 0; i < intAddRS.length; i++) {
            intAddRS[i].executeReservationStation();
        }
    }

    public int getNumOfDependencies(String tag) {
        int count = 0;
        for (int i = 0; i < intAddRS.length; i++) {
            if (intAddRS[i].qJ.equals(tag) || intAddRS[i].qK.equals(tag)) {
                count++;
            }
        }

        for (int i = 0; i < intMultRS.length; i++) {
            if (intMultRS[i].qJ.equals(tag) || intMultRS[i].qK.equals(tag)) {
                count++;
            }
        }
        return count;
    }

    public IntegerReservationStation[] getIntAddRS() {
        return intAddRS;
    }

    public IntegerReservationStation[] getIntMultRS() {
        return intMultRS;
    }
}
