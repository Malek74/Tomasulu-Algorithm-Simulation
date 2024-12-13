package views;

public class floatRegisterData {

    private String name;
    private double value;
    private String qi;

    public floatRegisterData(String name, double value, String qi) {
        this.name = name;
        this.value = value;
        this.qi = qi;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String getQi() {
        return qi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

}