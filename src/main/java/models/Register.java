package models;

import controllers.TomasuloInputController;

public class Register {
	private String name; // esm el register
	private String Qi;
	private MemoryBlock value;

	public Register(String name) {
		this.name = name;
		this.Qi = "";
		this.value = new MemoryBlock(TomasuloInputController.blockSize);
	}

	public Register(String name, MemoryBlock value, String Qi) {
		this.name = name;
		this.Qi = Qi;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getQi() {
		return Qi;
	}

	public void setQi(String Qi) {
		this.Qi = Qi;
	}

	public float getValue() {
		return value.translateWordToFloat();
	}

	public MemoryBlock getMemoryBlock() {
		return value;
	}

	public void setValue(MemoryBlock value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Register: " + name + ", Qi: " + (Qi == null ? " " : Qi) + ", Value: " + value;
	}

	public void updateRegister(String tag, MemoryBlock value) {
		if (Qi.equals(tag)) {
			this.value = value;
			this.Qi = "0";
		}
	}

}