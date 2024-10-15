package controller;

import utils.generalUtils.AnsiColorsUtil;

public class GsonController {
  public static void printJsonString(String gsonString) {
    System.out.println();
    System.out.println(AnsiColorsUtil.MEDIUM_GRAY.getCode() + gsonString + AnsiColorsUtil.WHITE.getCode());
  }
}
