package models;

public class MemoryBlock {
    private final int address;
    private final byte[] data;

    public MemoryBlock(int address, int size) {
        this.address = address;
        this.data = new byte[size];
    }

    public int getAddress() {
        return address;
    }

    public byte[] getData() {
        return data;
    }

    public byte readByte(int offset) {
        if (offset < 0 || offset >= data.length) {
            throw new IndexOutOfBoundsException("Offset out of range");
        }
        return data[offset];
    }

    public void writeByte(int offset, byte value) {
        if (offset < 0 || offset >= data.length) {
            throw new IndexOutOfBoundsException("Offset out of range");
        }
        data[offset] = value;
    }

    public void writeBytes(int offset, byte[] values) {
        if (offset < 0 || offset + values.length > data.length) {
            throw new IndexOutOfBoundsException("Offset out of range");
        }
        System.arraycopy(values, 0, data, offset, values.length);
    }
}
