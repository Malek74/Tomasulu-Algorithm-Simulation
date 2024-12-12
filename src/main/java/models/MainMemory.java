package models;

import models.MemoryBlock;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class MainMemory {
    private final int blockSize; // Size of each block in bytes
    private final Map<Integer, MemoryBlock> memoryBlocks;

    public MainMemory(int blockSize) {
        this.blockSize = blockSize;
        this.memoryBlocks = new HashMap<>();
    }

    public int getBlockSize() {
        return blockSize;
    }

    public MemoryBlock getBlock(int address) {
        return memoryBlocks.computeIfAbsent(address, addr -> new MemoryBlock(addr, blockSize));
    }

    public byte readByte(int address) {
        int blockAddress = (address / blockSize) * blockSize;
        int offset = address % blockSize;
        return getBlock(blockAddress).readByte(offset);
    }

    public void writeByte(int address, byte value) {
        int blockAddress = (address / blockSize) * blockSize;
        int offset = address % blockSize;
        getBlock(blockAddress).writeByte(offset, value);
    }

    public void writeBytes(int startAddress, byte[] values) {
        for (int i = 0; i < values.length; i++) {
            writeByte(startAddress + i, values[i]);
        }
    }

    public byte[] readFromMemory(int address, int numBytes) {
        byte[] result = new byte[numBytes];
        for (int i = 0; i < numBytes; i++) {
            result[i] = readByte(address + i); // Use the existing readByte method
        }
        return result;
    }

    public float translateWordToFloat(int address) {
        // Ensure we're reading exactly 4 bytes (size of a float)
        byte[] wordBytes = readFromMemory(address, 4); // Fetch the 4 bytes
        return ByteBuffer.wrap(wordBytes).getFloat(); // Convert to float
    }

    public float getWordFloat(int address) {
        return translateWordToFloat(address);
    }

    public void writeWordToMemory(int address, float value) {
        // Convert the float value to 4 bytes
        byte[] wordBytes = ByteBuffer.allocate(4).putFloat(value).array();

        // Write the bytes to memory, starting at the specified address
        writeBytes(address, wordBytes);
    }
}
