package models;

import javafx.scene.transform.Translate;
import java.nio.ByteBuffer;

public class MemoryBlock {
    private final int address;
    private final byte[] data;

    public MemoryBlock(int address, int size) {
        this.address = address;
        this.data = new byte[size];
    }

    public MemoryBlock(int size) {
        this.address = 0;
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

    public float translateWordToFloat() {
        // Ensure we're reading exactly 4 bytes (size of a float)
        byte[] wordBytes = this.data; // Fetch the 4 bytes
        return ByteBuffer.wrap(wordBytes).getFloat(); // Convert to float
    }

    public double translateWordToDouble() {
        // Ensure we're reading exactly 4 bytes (size of a float)
        byte[] wordBytes = this.data; // Fetch the 4 bytes
        return ByteBuffer.wrap(wordBytes).getDouble(); // Convert to float
    }

    public int translateWordToInt() {
        // Ensure we're reading exactly 4 bytes (size of a float)
        byte[] wordBytes = this.data; // Fetch the 4 bytes
        return ByteBuffer.wrap(wordBytes).getInt(); // Convert to float
    }

    public long translateWordToLong() {
        byte[] wordBytes = this.data; // Fetch the 4 bytes
        return ByteBuffer.wrap(wordBytes).getLong(); // Convert to float
    }


}
