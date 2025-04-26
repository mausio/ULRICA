package org.ulrica.infrastructure.adapter;

import java.util.Scanner;

import org.ulrica.application.port.out.UserInputPortInterface;

public class ConsoleUserInputAdapter implements UserInputPortInterface {
    private final Scanner scanner;
    
    public ConsoleUserInputAdapter(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String readLine() {
        return scanner.nextLine().trim();
    }
    
    @Override
    public int readInt() {
        try {
            int result = scanner.nextInt();
            scanner.nextLine(); 
            return result;
        } catch (Exception e) {
            
            scanner.nextLine();
            return 0; 
        }
    }
    
    @Override
    public double readDouble() {
        try {
            double result = scanner.nextDouble();
            scanner.nextLine(); 
            return result;
        } catch (Exception e) {
            
            scanner.nextLine();
            return 0.0; 
        }
    }
    
    @Override
    public boolean readBoolean(String yesOption, String noOption) {
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals(yesOption.toLowerCase()) || input.equals("y")) {
            return true;
        } else if (input.equals(noOption.toLowerCase()) || input.equals("n")) {
            return false;
        }
        
        return false;
    }
} 