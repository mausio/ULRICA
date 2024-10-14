package controller;

import utils.generalUtils.AnsiColorsUtil;

public class SystemMessagesController {
  public static void urgentMessage(String message) {
    System.out.println(AnsiColorsUtil.YELLOW.getCode() + "→ " + message + AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void imperativeMessage(String message) {
    System.out.println(AnsiColorsUtil.RED.getCode() + "→ Attention:" + message + AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void stepperMessage(String message) {
    System.out.println(AnsiColorsUtil.BLUE.getCode() + message + AnsiColorsUtil.WHITE.getCode());
  }
  
  //TODO: Add error message; Maybe in red with a blitz
}
