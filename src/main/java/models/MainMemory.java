package models;

public class MainMemory {
    private String[] memory;

    

    public MainMemory(int size) {
        memory = new String[size];
        String[] dummyData = {
                "00000000", // Byte 0
                "00000000", // Byte 1
                "00000000", // Byte 2
                "00000000", // Byte 3
                "00000000", // Byte 4
                "00000000", // Byte 5
                "00000000", // Byte 6
                "00000011", // Byte 7
                "00000001", // Byte 8
                "10101010", // Byte 9
                "10101010", // Byte 10
                "10101010", // Byte 11
                "11001100", // Byte 12
                "11001100", // Byte 13
                "11001100", // Byte 14
                "11001100" // Byte 15
        };

        for (int i = 0; i < size; i++) {
            memory[i] = dummyData[i % dummyData.length];
        }
    }

    public int size() {
        return memory.length;
    }

    
    public String get(int index){
        return memory[index];
    }

    public String load(int index, int bytesNum) {
        String res = "";
        for (int i = 0; i < bytesNum; i++) {
            if (memory[index + i] == null) {
                return null;
            }
            res += memory[index + i];
        }
        return res;
    }

    public void store(int index, String data) {
        int j=0;
        for (int i = 0; i < data.length(); i += 8) {

            memory[index + j] = data.substring(i, i + 8);
            j++;
        }
    }

    public static int binaryToDecimal(String binaryString) {
        if (binaryString == null || binaryString.isEmpty()) {
            throw new IllegalArgumentException("Input string must not be null or empty.");
        }

        int decimalValue = 0;
        int length = binaryString.length();

        for (int i = 0; i < length; i++) {
            char bit = binaryString.charAt(length - 1 - i); // Start from the least significant bit
            if (bit == '1') {
                decimalValue += Math.pow(2, i); // Add the power of 2 if the bit is 1
            } else if (bit != '0') {
                throw new IllegalArgumentException(
                        "Input string contains invalid characters. Only '0' and '1' are allowed.");
            }
        }

        return decimalValue;
    }

    public static String decimalToBinary(int decimal, int bytes) {
        if (decimal < 0) {
            throw new IllegalArgumentException("Input must be a non-negative integer.");
        }
        if (bytes <= 0) {
            throw new IllegalArgumentException("Number of bytes must be greater than 0.");
        }

        StringBuilder binaryString = new StringBuilder();

        if (decimal == 0) {
            // Return a string of zeros of the desired length
            return "0".repeat(bytes * 8);
        }

        while (decimal > 0) {
            int remainder = decimal % 2;
            binaryString.append(remainder); // Append the remainder (0 or 1)
            decimal /= 2; // Divide by 2 to shift right
        }

        binaryString.reverse(); // Reverse the string to get the correct binary order

        // Calculate the required length in bits (bytes * 8)
        int requiredLength = bytes * 8;

        // Add leading zeros to make the length equal to requiredLength
        while (binaryString.length() < requiredLength) {
            binaryString.insert(0, "0");
        }

        // If the number is too large for the specified bytes, truncate the most
        // significant bits
        if (binaryString.length() > requiredLength) {
            binaryString = new StringBuilder(binaryString.substring(binaryString.length() - requiredLength));
        }

        return binaryString.toString();
    }

}
