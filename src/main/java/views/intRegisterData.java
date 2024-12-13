package views;

public class intRegisterData {
    private String name;
    private long value;
    private String qi;

    public intRegisterData(String name, long value, String qi) {
        this.name = name;
        this.value = value;
        this.qi = qi;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }

    public String getQi() {
        return qi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }
}
