package org.ulrica.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;

import org.ulrica.application.port.out.UserOutputPortInterface;

/**
 * A mock implementation of UserOutputPortInterface for testing.
 * Captures output for verification in tests.
 */
public class MockUserOutputAdapter implements UserOutputPortInterface {
    
    private final List<String> outputLines = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();
    private final List<String> prompts = new ArrayList<>();
    
    @Override
    public void displayLine(String message) {
        outputLines.add(message);
        messages.add(message);
    }
    
    @Override
    public void display(String message) {
        messages.add(message);
    }
    
    @Override
    public void displayPrompt(String prompt) {
        prompts.add(prompt);
        messages.add(prompt);
    }
    
    /**
     * Get all complete lines that were output
     * @return List of output lines
     */
    public List<String> getOutputLines() {
        return new ArrayList<>(outputLines);
    }
    
    /**
     * Get all messages that were output (including partial lines)
     * @return List of output messages
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
    
    /**
     * Get all prompts that were output
     * @return List of prompts
     */
    public List<String> getPrompts() {
        return new ArrayList<>(prompts);
    }
    
    /**
     * Get the last output line
     * @return The last complete line output, or null if none
     */
    public String getLastLine() {
        return outputLines.isEmpty() ? null : outputLines.get(outputLines.size() - 1);
    }
    
    /**
     * Get the last message
     * @return The last message output, or null if none
     */
    public String getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
    
    /**
     * Get the last prompt
     * @return The last prompt output, or null if none
     */
    public String getLastPrompt() {
        return prompts.isEmpty() ? null : prompts.get(prompts.size() - 1);
    }
    
    /**
     * Check if a specific text was output
     * @param text The text to search for
     * @return true if the text appears in any output
     */
    public boolean containsOutput(String text) {
        for (String message : messages) {
            if (message.contains(text)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if a specific prompt was output
     * @param promptText The prompt text to search for
     * @return true if the prompt appears in any output
     */
    public boolean containsPrompt(String promptText) {
        for (String prompt : prompts) {
            if (prompt.contains(promptText)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Count how many times a specific text was output
     * @param text The text to search for
     * @return Count of occurrences
     */
    public int countOutputs(String text) {
        int count = 0;
        for (String message : messages) {
            if (message.contains(text)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Clear all captured output
     */
    public void clearOutput() {
        outputLines.clear();
        messages.clear();
        prompts.clear();
    }
    
    /**
     * Get total number of output lines
     * @return Count of lines
     */
    public int getOutputLineCount() {
        return outputLines.size();
    }
    
    /**
     * Get total number of output messages
     * @return Count of messages
     */
    public int getMessageCount() {
        return messages.size();
    }
    
    /**
     * Get total number of prompts
     * @return Count of prompts
     */
    public int getPromptCount() {
        return prompts.size();
    }
} 