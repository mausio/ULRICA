package utils.generalUtils;

import controller.SystemMessagesController;

public class ConsoleInteractorUtil {
  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  
  public static void stepper(String message){
    System.out.println();
    ConsoleInteractorUtil.clear();
    SystemMessagesController.stepperMessage(message);
    SleepUtil.waitForFSeconds(1.0);
  }
}
