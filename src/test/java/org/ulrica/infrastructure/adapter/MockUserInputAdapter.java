package org.ulrica.infrastructure.adapter;

import java.util.ArrayDeque;
import java.util.Queue;

import org.ulrica.application.port.out.UserInputPortInterface;

public class MockUserInputAdapter implements UserInputPortInterface {
    
    private final Queue<String> stringInputs = new ArrayDeque<>();
    private final Queue<Integer> intInputs = new ArrayDeque<>();
    private final Queue<Double> doubleInputs = new ArrayDeque<>();
    private final Queue<Boolean> booleanInputs = new ArrayDeque<>();
    
    private String defaultStringInput = "";
    private int defaultIntInput = 0;
    private double defaultDoubleInput = 0.0;
    private boolean defaultBooleanInput = false;
    
    public void addStringInput(String input) {
        stringInputs.add(input);
    }
    
    public void addStringInputs(String... inputs) {
        for (String input : inputs) {
            stringInputs.add(input);
        }
    }
    
    public void addIntInput(int input) {
        intInputs.add(input);
    }
    
    public void addIntInputs(int... inputs) {
        for (int input : inputs) {
            intInputs.add(input);
        }
    }
    
    public void addDoubleInput(double input) {
        doubleInputs.add(input);
    }
    
    public void addDoubleInputs(double... inputs) {
        for (double input : inputs) {
            doubleInputs.add(input);
        }
    }
    
    public void addBooleanInput(boolean input) {
        booleanInputs.add(input);
    }
    
    public void addBooleanInputs(boolean... inputs) {
        for (boolean input : inputs) {
            booleanInputs.add(input);
        }
    }

    @Override
    public String readLine() {
        return stringInputs.isEmpty() ? defaultStringInput : stringInputs.poll();
    }

    @Override
    public int readInt() {
        return intInputs.isEmpty() ? defaultIntInput : intInputs.poll();
    }

    @Override
    public double readDouble() {
        return doubleInputs.isEmpty() ? defaultDoubleInput : doubleInputs.poll();
    }
    
    @Override
    public boolean readBoolean(String yesOption, String noOption) {
        return booleanInputs.isEmpty() ? defaultBooleanInput : booleanInputs.poll();
    }
    
    public void setDefaultStringInput(String defaultInput) {
        this.defaultStringInput = defaultInput;
    }
    
    public void setDefaultIntInput(int defaultInput) {
        this.defaultIntInput = defaultInput;
    }
    
    public void setDefaultDoubleInput(double defaultInput) {
        this.defaultDoubleInput = defaultInput;
    }
    
    public void setDefaultBooleanInput(boolean defaultInput) {
        this.defaultBooleanInput = defaultInput;
    }
    
    public boolean hasStringInputs() {
        return !stringInputs.isEmpty();
    }
    
    public boolean hasIntInputs() {
        return !intInputs.isEmpty();
    }
    
    public boolean hasDoubleInputs() {
        return !doubleInputs.isEmpty();
    }
    
    public boolean hasBooleanInputs() {
        return !booleanInputs.isEmpty();
    }
    
    public void clearInputs() {
        stringInputs.clear();
        intInputs.clear();
        doubleInputs.clear();
        booleanInputs.clear();
    }
} 