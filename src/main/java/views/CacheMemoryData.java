package views;

public class CacheMemoryData {
    private int address;
    private String content;

    public CacheMemoryData(int address, String content) {
        this.address = address;
        this.content = content;
    }

    public int getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }
}
