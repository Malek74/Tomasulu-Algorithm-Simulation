package models;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class RegisterFile {
	private Hashtable<String, Register> registers;

	// todo:needs to start from zero
	public RegisterFile(String prefix, int count) {
		registers = new Hashtable<>();
		for (int i = 0; i <= count; i++) {
			registers.put(prefix + i, new Register(prefix + i));
			registers.get(prefix + i).setValue(0);
			registers.get(prefix + i).setQi("0");
		}
	}



	public int size() {
		return registers.size();
	}

	public Register getRegister(String name) {
		return registers.get(name);
	}

	public void updateRegisterDuetoIssue(String name, String Qi) {
		// low malek msh haye3melha hda5al el inst kolaha as a string w a3mle switch
		// case 3ala asas awel letter
		// fel inst then a add 3aleih counter bta3o L1 wla L2 w a7otaha fel Qi
		Register register = registers.get(name);
		if (register != null) {
			register.setQi(Qi);
		} else {
			System.out.println("Register " + name + " not found!");
		}
	}

	public void updateRegister(String tag, float value, String name) {
		for (int i = 0; i < registers.size(); i++) {
			registers.get(name + i).updateRegister(tag, value);
		}
	}

	public void initializeRegister(String name, float value) {
		Register register = registers.get(name);
		if (register != null) {
			register.setValue(value);
		} else {
			System.out.println("Register " + name + " not found!");
		}
	}

	public void printRegisterFile() {
		for (Register register : registers.values()) {
			System.out.println(register);
		}
	}

	public int countDependencies(String Key, String tag) {
		int count = 0;
		for (int i = 0; i < registers.size(); i++) {
			if (tag.equals(registers.get(Key + i).getQi())) {
				count++;
			}
		}
		return count;
	}
}