package models;
import java.util.Scanner;

public class TomasuloRegisterFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RegisterFile intRegisterFile = new RegisterFile("R", 32);
        RegisterFile floatRegisterFile = new RegisterFile("F", 32);

        System.out.println("Initializing Register Files...");
        
        System.out.println("Enter initial values for integer registers (R1 to R32):");
        for (int i = 1; i <= 32; i++) {
            System.out.print("R" + i + ": ");
            double value = scanner.nextDouble();
            intRegisterFile.initializeRegister("R" + i, value);
        }

        
        System.out.println("Enter initial values for floating-point registers (F1 to F32):");
        for (int i = 1; i <= 32; i++) {
            System.out.print("F" + i + ": ");
            double value = scanner.nextDouble();
            floatRegisterFile.initializeRegister("F" + i, value);
        }

        System.out.println("\nInteger Register File:");
        intRegisterFile.printRegisterFile();

        System.out.println("\nFloating-Point Register File:");
        floatRegisterFile.printRegisterFile();

        scanner.close();
    }
}