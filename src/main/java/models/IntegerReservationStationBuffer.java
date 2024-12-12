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
        
        int index = ((int) tag.charAt(2)) - 1;
        int value = 0;

        if (tag.contains("MR")) {
            value = intMultRS[index].getVJ() * intMultRS[index].getVK();
            mainController.int
        } else if (tag.contains("AR")) {
            value = intAddRS[index].getVJ() + intAddRS[index].getVK();
        }

        updateReservationStation(tag, value);

    }

    private void updateReservationStation(String tag, int value) {
        for (int i = 0; i < intAddRS.length; i++) {
            intAddRS[i].updateReservationStation(tag, value);
        }

        for (int i = 0; i < intMultRS.length; i++) {
            intMultRS[i].updateReservationStation(tag, value);
        }

        // update the register file
        // todo:call the register file update function

        // update the store buffer
        // todo:call the store buffer update function
    }

    public boolean issueInstruction(Instruction instruction, operation type, RegisterFile registerFile) {

        // split the instruction
        String[] operands = instruction.getInstruction().split(" ");

        Register register;

        switch (type) {
            case ADD:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {

                        intAddRS[i].setOperation(type);
                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], intAddRS[i].getTagName());

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
                        // todo:add the logic to issue the instruction in the reservation station
                        return true;
                    }
                }
                return false;
            case MULT:
                for (int i = 0; i < intMultRS.length; i++) {
                    if (!intMultRS[i].isBusy()) {
                        intMultRS[i].setBusy(true);
                        intMultRS[i].setOperation(type);

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
                        return true;
                    }
                }
                return false;
            case SUB:
                for (int i = 0; i < intAddRS.length; i++) {
                    if (!intAddRS[i].isBusy()) {

                        intAddRS[i].setBusy(true);
                        intAddRS[i].setOperation(type);

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
                        return true;
                    }
                }
                return false;
            case DIV:
                for (int i = 0; i < intMultRS.length; i++) {
                    if (!intMultRS[i].isBusy()) {
                        intMultRS[i].setBusy(true);
                        intMultRS[i].setOperation(type);

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
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public void executeInstruction() {
        for (int i = 0; i < intMultRS.length; i++) {
            if (intMultRS[i].isBusy() && intMultRS[i].isReady) {
                intMultRS[i].setTimeLeft(intMultRS[i].getTimeLeft() - 1);
            }
        }
        for (int i = 0; i < intAddRS.length; i++) {
            if (intAddRS[i].isBusy() && intAddRS[i].isReady) {
                intAddRS[i].setTimeLeft(intAddRS[i].getTimeLeft() - 1);
            }
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
}
