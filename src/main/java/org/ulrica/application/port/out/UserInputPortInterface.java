package org.ulrica.application.port.out;

public interface UserInputPortInterface {
    String readLine();
    int readInt();
    double readDouble();
    boolean readBoolean(String yesOption, String noOption);
} 