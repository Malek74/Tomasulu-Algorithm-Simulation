package models;

import models.MemoryBlock;

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
}
