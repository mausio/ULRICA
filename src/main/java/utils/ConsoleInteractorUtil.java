package utils;

public class ConsoleInteractorUtil {
  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  
  public static void stepper(String message){
    System.out.println();
    ConsoleInteractorUtil.clear();
    System.out.println(AnsiColorsUtil.BLUE.getCode() + message + AnsiColorsUtil.WHITE.getCode());
    SleepUtil.waitForFSeconds(1.0);
  }
}
