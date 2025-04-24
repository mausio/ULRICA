package org.ulrica.infrastructure.adapter;

import org.ulrica.application.port.out.UserOutputPortInterface;

public class ConsoleUserOutputAdapter implements UserOutputPortInterface {
    
    @Override
    public void display(String message) {
        System.out.print(message);
    }
    
    @Override
    public void displayLine(String message) {
        System.out.println(message);
    }
    
    @Override
    public void displayPrompt(String prompt) {
        System.out.print(prompt);
    }
} 