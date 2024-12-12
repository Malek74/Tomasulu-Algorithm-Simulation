package models;

import controllers.mainController;
import models.FloatReservationStation;
import models.Instruction;
import models.operation;

public class FloatReservationStationBuffer {
    FloatReservationStation[] floatMultRS;
    FloatReservationStation[] floatAddRS;

    public FloatReservationStationBuffer(int multSize, int addSize) {
        floatMultRS = new FloatReservationStation[multSize];
        floatAddRS = new FloatReservationStation[addSize];

        for (int i = 0; i < multSize; i++) {
            floatMultRS[i] = new FloatReservationStation();
            floatMultRS[i].setTagName("MF" + i);
        }

        for (int i = 0; i < addSize; i++) {
            floatAddRS[i] = new FloatReservationStation();
            floatAddRS[i].setTagName("AF" + i);
        }
    }

    public boolean issueInstruction(Instruction instruction, operation type, RegisterFile registerFile) {

        // split the instruction
        String[] operands = instruction.getInstruction().split(" ");

        Register register;
        switch (type) {
            case ADD:
                for (int i = 0; i < floatAddRS.length; i++) {
                    if (!floatAddRS[i].isBusy()) {

                        floatAddRS[i].setBusy(true);
                        floatAddRS[i].setOperation(type);

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVJ(register.getValue());
                            floatAddRS[i].setReady(true);
                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVK(register.getValue());
                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // todo:add the logic to issue the instruction in the reservation station
                        return true;
                    }
                }
                return false;
            case MULT:
                for (int i = 0; i < floatMultRS.length; i++) {
                    if (!floatMultRS[i].isBusy()) {
                        floatMultRS[i].setBusy(true);
                        floatMultRS[i].setOperation(type);

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVJ(register.getValue());
                            floatMultRS[i].setReady(true);
                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVK(register.getValue());

                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        return true;
                    }
                }
                return false;

            case SUB:
                for (int i = 0; i < floatAddRS.length; i++) {
                    if (!floatAddRS[i].isBusy()) {

                        floatAddRS[i].setBusy(true);
                        floatAddRS[i].setOperation(type);

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVJ(register.getValue());
                            floatAddRS[i].setReady(true);
                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVK(register.getValue());

                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // todo:add the logic to issue the instruction in the reservation station
                        return true;
                    }
                }
                return false;
            case DIV:
                for (int i = 0; i < floatMultRS.length; i++) {
                    if (!floatMultRS[i].isBusy()) {
                        floatMultRS[i].setBusy(true);
                        floatMultRS[i].setOperation(type);

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVJ(register.getValue());
                            floatMultRS[i].setReady(true);
                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVK(register.getValue());
                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        return true;
                    }
                }
                return false;
        }
        return false;

    }

    public void executeInstruction() {
        for (int i = 0; i < floatMultRS.length; i++) {
            if (floatMultRS[i].isBusy() && floatMultRS[i].isReady) {
                floatMultRS[i].setTimeLeft(floatMultRS[i].getTimeLeft() - 1);
            }
        }
        for (int i = 0; i < floatAddRS.length; i++) {
            if (floatAddRS[i].isBusy() && floatAddRS[i].isReady) {
                floatAddRS[i].setTimeLeft(floatAddRS[i].getTimeLeft() - 1);
            }
        }
    }

    public int getNumOfDependencies(String tag) {
        int count = 0;
        for (int i = 0; i < floatAddRS.length; i++) {
            if (floatAddRS[i].qJ.equals(tag) || floatAddRS[i].qK.equals(tag)) {
                count++;
            }
        }

        for (int i = 0; i < floatMultRS.length; i++) {
            if (floatAddRS[i].qJ.equals(tag) || floatAddRS[i].qK.equals(tag)) {
                count++;
            }
        }
        return count;
    }

    public void writeBack(String tagName) {
        int index = ((int) tagName.charAt(2)) - 1;

        float value = 0;

        if (tagName.contains("MF")) {
            value = (float) (floatMultRS[index].getVJ() * floatMultRS[index].getVK());

            floatMultRS[index].clearReservationStation();
        } else if (tagName.contains("AF")) {
            value = (float) (floatMultRS[index].getVJ() + floatMultRS[index].getVK());
            floatAddRS[index].clearReservationStation();
        }

        // update reservation stations due to write back
        updateReservationStationBuffer(tagName, value);

        // update all store buffer entries that depend on this tag
        mainController.storeBuffer.updateStoreBuffer(tagName, value);

        // todo:update all register files that depend on this tag
        mainController.registerFloat.updateRegister(tagName, value, "F");
        mainController.registerFloat.updateRegister(tagName, value, "R");

    }

    public void updateReservationStationBuffer(String tag, float value) {

        // update all reservation stations that depend on this tag
        for (int i = 0; i < floatMultRS.length; i++) {
            floatMultRS[i].updateReservationStation(tag, value);
        }
        for (int i = 0; i < floatAddRS.length; i++) {
            floatAddRS[i].updateReservationStation(tag, value);
        }

    }

    public void printRS() {
        System.out.println("Printing the float Multiplication reservation station buffer\n");
        for (int i = 0; i < floatMultRS.length; i++) {
            System.out.println(floatMultRS[i].toString());
        }

        System.out.println("Printing the float addition reservation station buffer\n");
        for (int i = 0; i < floatAddRS.length; i++) {
            System.out.println(floatAddRS[i].toString());
        }
    }

}
