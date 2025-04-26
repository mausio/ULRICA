package org.ulrica.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;

import org.ulrica.application.port.out.UserOutputPortInterface;

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
    
    public List<String> getOutputLines() {
        return new ArrayList<>(outputLines);
    }
    
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
    
    public List<String> getPrompts() {
        return new ArrayList<>(prompts);
    }
    
    public String getLastLine() {
        return outputLines.isEmpty() ? null : outputLines.get(outputLines.size() - 1);
    }
    
    public String getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }
    
    public String getLastPrompt() {
        return prompts.isEmpty() ? null : prompts.get(prompts.size() - 1);
    }
    
    public boolean containsOutput(String text) {
        for (String message : messages) {
            if (message.contains(text)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsPrompt(String promptText) {
        for (String prompt : prompts) {
            if (prompt.contains(promptText)) {
                return true;
            }
        }
        return false;
    }
    
    public int countOutputs(String text) {
        int count = 0;
        for (String message : messages) {
            if (message.contains(text)) {
                count++;
            }
        }
        return count;
    }
    
    public void clearOutput() {
        outputLines.clear();
        messages.clear();
        prompts.clear();
    }
    
    public int getOutputLineCount() {
        return outputLines.size();
    }
    
    public int getMessageCount() {
        return messages.size();
    }
    
    public int getPromptCount() {
        return prompts.size();
    }
} 