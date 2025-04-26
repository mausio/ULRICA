package org.ulrica.infrastructure.adapter;

import java.util.ArrayDeque;
import java.util.Queue;

import org.ulrica.application.port.out.UserInputPortInterface;

/**
 * A mock implementation of UserInputPortInterface for testing.
 * Allows tests to pre-load inputs that will be returned by each call.
 */
public class MockUserInputAdapter implements UserInputPortInterface {
    
    private final Queue<String> stringInputs = new ArrayDeque<>();
    private final Queue<Integer> intInputs = new ArrayDeque<>();
    private final Queue<Double> doubleInputs = new ArrayDeque<>();
    private final Queue<Boolean> booleanInputs = new ArrayDeque<>();
    
    private String defaultStringInput = "";
    private int defaultIntInput = 0;
    private double defaultDoubleInput = 0.0;
    private boolean defaultBooleanInput = false;
    
    /**
     * Add string input to be returned by readLine()
     * @param input The string to return
     */
    public void addStringInput(String input) {
        stringInputs.add(input);
    }
    
    /**
     * Add multiple string inputs
     * @param inputs The strings to return in sequence
     */
    public void addStringInputs(String... inputs) {
        for (String input : inputs) {
            stringInputs.add(input);
        }
    }
    
    /**
     * Add integer input to be returned by readInt()
     * @param input The integer to return
     */
    public void addIntInput(int input) {
        intInputs.add(input);
    }
    
    /**
     * Add multiple integer inputs
     * @param inputs The integers to return in sequence
     */
    public void addIntInputs(int... inputs) {
        for (int input : inputs) {
            intInputs.add(input);
        }
    }
    
    /**
     * Add double input to be returned by readDouble()
     * @param input The double to return
     */
    public void addDoubleInput(double input) {
        doubleInputs.add(input);
    }
    
    /**
     * Add multiple double inputs
     * @param inputs The doubles to return in sequence
     */
    public void addDoubleInputs(double... inputs) {
        for (double input : inputs) {
            doubleInputs.add(input);
        }
    }
    
    /**
     * Add boolean input to be returned by readBoolean()
     * @param input The boolean to return
     */
    public void addBooleanInput(boolean input) {
        booleanInputs.add(input);
    }
    
    /**
     * Add multiple boolean inputs
     * @param inputs The booleans to return in sequence
     */
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
    
    /**
     * Set default string to return when queue is empty
     * @param defaultInput The default string
     */
    public void setDefaultStringInput(String defaultInput) {
        this.defaultStringInput = defaultInput;
    }
    
    /**
     * Set default integer to return when queue is empty
     * @param defaultInput The default integer
     */
    public void setDefaultIntInput(int defaultInput) {
        this.defaultIntInput = defaultInput;
    }
    
    /**
     * Set default double to return when queue is empty
     * @param defaultInput The default double
     */
    public void setDefaultDoubleInput(double defaultInput) {
        this.defaultDoubleInput = defaultInput;
    }
    
    /**
     * Set default boolean to return when queue is empty
     * @param defaultInput The default boolean
     */
    public void setDefaultBooleanInput(boolean defaultInput) {
        this.defaultBooleanInput = defaultInput;
    }
    
    /**
     * Check if there are any string inputs left
     * @return true if there are pending string inputs
     */
    public boolean hasStringInputs() {
        return !stringInputs.isEmpty();
    }
    
    /**
     * Check if there are any integer inputs left
     * @return true if there are pending integer inputs
     */
    public boolean hasIntInputs() {
        return !intInputs.isEmpty();
    }
    
    /**
     * Check if there are any double inputs left
     * @return true if there are pending double inputs
     */
    public boolean hasDoubleInputs() {
        return !doubleInputs.isEmpty();
    }
    
    /**
     * Check if there are any boolean inputs left
     * @return true if there are pending boolean inputs
     */
    public boolean hasBooleanInputs() {
        return !booleanInputs.isEmpty();
    }
    
    /**
     * Clear all queued inputs
     */
    public void clearInputs() {
        stringInputs.clear();
        intInputs.clear();
        doubleInputs.clear();
        booleanInputs.clear();
    }
} 