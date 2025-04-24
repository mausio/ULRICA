package org.ulrica.application.port.out;

public interface UserOutputPortInterface {
    void display(String message);
    void displayLine(String message);
    void displayPrompt(String prompt);
} 