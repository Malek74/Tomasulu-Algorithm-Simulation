package models;

import controllers.mainController;
import models.FloatReservationStation;
import models.Instruction;
import models.operation;

public class FloatReservationStationBuffer {
    public FloatReservationStation[] floatMultRS;
    public FloatReservationStation[] floatAddRS;

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

    public boolean issueInstruction(Instruction instruction, operation type, RegisterFile registerFile,int time) {

        // split the instruction
        String[] operands = instruction.getInstruction().split(" ");

        Register register;
        switch (type) {
            case ADD:
                for (int i = 0; i < floatAddRS.length; i++) {
                    if (!floatAddRS[i].isBusy()) {

                        floatAddRS[i].setBusy(true);
                        floatAddRS[i].setOperation(type);
                        floatAddRS[i].timeLeft=time;


                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVJ(register.getMemoryBlock());
                            floatAddRS[i].setReady(true);
                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVK(register.getMemoryBlock());
                        } else {
                            floatAddRS[i].setQK(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

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
                        floatMultRS[i].timeLeft=time;


                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVJ(register.getMemoryBlock());
                            floatMultRS[i].setReady(true);
                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVK(register.getMemoryBlock());

                        } else {
                            floatMultRS[i].setQK(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatMultRS[i].getTagName());

                        return true;
                    }
                }
                return false;

            case SUB:
                for (int i = 0; i < floatAddRS.length; i++) {
                    if (!floatAddRS[i].isBusy()) {

                        floatAddRS[i].setBusy(true);
                        floatAddRS[i].setOperation(type);
                        floatAddRS[i].timeLeft=time;


                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVJ(register.getMemoryBlock());
                            floatAddRS[i].setReady(true);
                        } else {
                            floatAddRS[i].setQJ(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatAddRS[i].setVK(register.getMemoryBlock());

                        } else {
                            floatAddRS[i].setQK(register.getQi());
                            floatAddRS[i].setReady(false);
                        }

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatAddRS[i].getTagName());

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
                        floatMultRS[i].timeLeft=time;


                        // check first operand in register file
                        register = registerFile.getRegister(operands[2]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVJ(register.getMemoryBlock());
                            floatMultRS[i].setReady(true);
                        } else {
                            floatMultRS[i].setQJ(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // check second operand in register file
                        register = registerFile.getRegister(operands[3]);

                        // todo:el default ehh neseet
                        if (register.getQi().equals("0")) {
                            floatMultRS[i].setVK(register.getMemoryBlock());
                        } else {
                            floatMultRS[i].setQK(register.getQi());
                            floatMultRS[i].setReady(false);
                        }

                        // set the destination register
                        registerFile.updateRegisterDuetoIssue(operands[1], floatMultRS[i].getTagName());

                        return true;
                    }
                }
                return false;
        }
        return false;

    }

    public void executeInstruction() {
        for (int i = 0; i < floatMultRS.length; i++) {
            floatMultRS[i].executeReservationStation();
        }
        for (int i = 0; i < floatAddRS.length; i++) {
            floatAddRS[i].executeReservationStation();
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
            if (floatMultRS[i].qJ.equals(tag) || floatMultRS[i].qK.equals(tag)) {
                count++;
            }
        }
        return count;
    }

    public void writeBack(String tagName) {
        int index = Integer.parseInt( tagName.charAt(2)+"") ;

        MemoryBlock value = 0;

        if (tagName.contains("MF")) {

            String op= String.valueOf(floatMultRS[index].getOperation());
            if(op.equals("MULT")){
            value = (float) (floatMultRS[index].getVJ() * floatMultRS[index].getVK());}
            if(op.equals("DIV")){
                value=(float) (floatMultRS[index].getVJ() / floatMultRS[index].getVK());
            }

            floatMultRS[index].clearReservationStation();
        } else if (tagName.contains("AF")) {
            String op= String.valueOf(floatMultRS[index].getOperation());
            if(op.equals("ADD")){
                value = (float) (floatMultRS[index].getVJ() + floatMultRS[index].getVK());}
            if(op.equals("SUB")){
                value=(float) (floatMultRS[index].getVJ() - floatMultRS[index].getVK());
            }
            floatAddRS[index].clearReservationStation();
        }

        // update reservation stations due to write back
        updateReservationStationBuffer(tagName, value);

        // update all store buffer entries that depend on this tag
        mainController.storeBuffer.updateStoreBuffer(tagName, value);

        // todo:update all register files that depend on this tag
        mainController.registerFloat.updateRegister(tagName, value, "F");
        mainController.registerInt.updateRegister(tagName, value, "R");

        // update all branches that depend on tag
        for (String branch : mainController.branchInstructionsBuffer.keySet()) {
            mainController.branchInstructionsBuffer.get(branch).updateDueToWriteBack(tagName, value);
        }

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

    public FloatReservationStation[] getFloatAddRS() {
        return floatAddRS;
    }

    public FloatReservationStation[] getFloatMultRS() {
        return floatMultRS;
    }
}
