package models;

public class Register {
	 private String name; 	//esm el register
	    private String Qi;   
	    private double value; 

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

	    public double getValue() {
	        return value;
	    }

	    public void setValue(double value) {
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return "Register: " + name + ", Qi: " + (Qi == null ? " " : Qi) + ", Value: " + value;
	    }
	}