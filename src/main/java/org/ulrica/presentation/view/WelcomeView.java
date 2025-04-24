package org.ulrica.presentation.view;

import org.ulrica.application.port.out.WelcomeOutputPortInterface;

public class WelcomeView implements WelcomeOutputPortInterface {

    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String WHITE = "\u001B[37m";

    @Override
    public void displayWelcomeMessage() {
        System.out.println(CYAN);
        System.out.println("            ██╗   ██╗██╗     ██████╗ ██╗ ██████╗ █████╗");
        System.out.println("            ██║   ██║██║     ██╔══██╗██║██╔════╝██╔══██╗");
        System.out.println("            ██║   ██║██║     ██████╔╝██║██║     ███████║");
        System.out.println("            ██║   ██║██║     ██╔══██╗██║██║     ██╔══██║");
        System.out.println("            ╚██████╔╝███████╗██║  ██║██║╚██████╗██║  ██║");
        System.out.println("             ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝");
        System.out.println();
        System.out.println(WHITE + "┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│      Welcome to ULRICA - Your Range & Destination Calculator       │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  Let's calculate the perfect route or range for your electric car! │");
        System.out.println("└--------------------------------------------------------------------┘");
        System.out.println();
    }

    @Override
    public void displayAttentionMessage() {
        System.out.println(RED +"→ Attention:");
        System.out.println("  This program's text is completely " + WHITE + "WHITE" + RED + ",");
        System.out.println("  so please consider using dark mode.");
        System.out.println(WHITE);
    }
} 