package models;

import controllers.mainController;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache {
    private final int capacity;
    private final Map<Integer, byte[]> cache;

    public Cache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, byte[]> eldest) {
//                System.out.println("removed LRU");
                return size() > capacity;
            }
        };
    }

    public byte readByte(int address) {
        MainMemory mainMemory = mainController.memory;
        int blockAddress = address / mainMemory.getBlockSize() * mainMemory.getBlockSize();

        if (!cache.containsKey(blockAddress)) {
            System.out.println("cache miss");
            // Cache miss, load block from main memory
            byte[] blockData = mainMemory.getBlock(blockAddress).getData();
            cache.put(blockAddress, blockData.clone());
            System.out.println("Fetched block "+ blockAddress+ " into cache");
        }

        int offset = address % mainMemory.getBlockSize();
        return cache.get(blockAddress)[offset];
    }

    public void writeByte(int address, byte value) {
        MainMemory mainMemory = mainController.memory;
        int blockAddress = address / mainMemory.getBlockSize() * mainMemory.getBlockSize();

        if (!cache.containsKey(blockAddress)) {
            // Cache miss, load block from main memory
            byte[] blockData = mainMemory.getBlock(blockAddress).getData();
            cache.put(blockAddress, blockData.clone());
        }

        int offset = address % mainMemory.getBlockSize();
        cache.get(blockAddress)[offset] = value;

        // Write-through policy
        mainMemory.writeByte(address, value);
    }

    public void displayCache() {
        cache.forEach((address, blockData) -> {
            System.out.print("Block Address: " + address + " | Data: ");
            for (byte b : blockData) {
                System.out.print( b);
            }
            System.out.println();
        });
    }
}
