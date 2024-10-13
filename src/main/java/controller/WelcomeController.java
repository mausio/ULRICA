package controller;

import utils.AnsiColorsUtil;

public class WelcomeController {
  public WelcomeController() {
    displayWelcomeMessage();
    displayAttentionMessage();
  }
  
  public static void displayWelcomeMessage() {
    System.out.println();
    System.out.println(AnsiColorsUtil.CYAN.getCode());
    System.out.println("            ██╗   ██╗██╗     ██████╗ ██╗ ██████╗ █████╗ ");
    System.out.println("            ██║   ██║██║     ██╔══██╗██║██╔════╝██╔══██╗");
    System.out.println("            ██║   ██║██║     ██████╔╝██║██║     ███████║");
    System.out.println("            ██║   ██║██║     ██╔══██╗██║██║     ██╔══██║");
    System.out.println("            ╚██████╔╝███████╗██║  ██║██║╚██████╗██║  ██║");
    System.out.println("             ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝ ╚═════╝╚═╝  ╚═╝");
    System.out.print(AnsiColorsUtil.WHITE.getCode());
    System.out.println("┌────────────────────────────────────────────────────────────────────┐");
    System.out.println("│      Welcome to ULRICA - Your Range & Destination Calculator       │");
    System.out.println("├────────────────────────────────────────────────────────────────────┤");
    System.out.println("│  Let's calculate the perfect route or range for your electric car! │");
    System.out.println("└--------------------------------------------------------------------┘");
  }
  
  public static void displayAttentionMessage() {
    System.out.println(AnsiColorsUtil.RED_BOLD.getCode() + "\n→ Attention: ");
    System.out.println(AnsiColorsUtil.RED.getCode() + "  This program's text is completely WHITE, \n  so " + "please consider " + "using dark mode.");
    System.out.println();
    System.out.println("  Thank you." + AnsiColorsUtil.WHITE.getCode() + "\n");
  }
}



