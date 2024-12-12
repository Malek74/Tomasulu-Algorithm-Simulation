package models;

public class Register {
	private String name; // esm el register
	private String Qi;
	private float value;

	public Register(String name) {
		this.name = name;
		this.Qi = null;
		this.value = 0;
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
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Register: " + name + ", Qi: " + (Qi == null ? " " : Qi) + ", Value: " + value;
	}

	public void updateRegister(String tag, float value) {
		if (Qi.equals(tag)) {
			this.value = value;
			this.Qi = null;
		}
	}

}