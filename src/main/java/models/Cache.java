package models;

import controllers.mainController;

public class Cache {
    private String[] cache;
    private int blockSize;

    public Cache(int size, int blockSize) {
        cache = new String[size];
        this.blockSize = blockSize;

    }

    public String read(int address, int numOfBytes) {
        String res = "";
        // Check if the block is in the cache
        for (int i = 0; i < numOfBytes; i++) {
            if (cache[address + i] == null) {
                return null;
            }
            res += cache[address + i];
        }
        return res;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int size() {
        return cache.length;
    }

    public String get(int index) {
        return cache[index];
    }

    public void write(int address, String data) {
        // Write data to cache
        int j = 0;
        System.out.println("Writing to cache: " + data);
        for (int i = 0; i < data.length(); i += 8) {

            cache[address + j] = data.substring(i, i + 8);
            j++;
        }
    }
}