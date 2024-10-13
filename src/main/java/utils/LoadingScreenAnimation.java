package utils;

public class LoadingScreenAnimation {
  public static void showLoadingAnimation(Double durationInSeconds) {
    String[] loadingStates = {"   ",".  ", ".. ", "..."};
    
    long startTime = System.currentTimeMillis();
    long endTime = (long) (startTime + (durationInSeconds * 1000));
    
    System.out.println(AnsiColorsUtil.DARK_GRAY.getCode() + "\n");
    
    while (System.currentTimeMillis() < endTime) {
      for (String state : loadingStates) {
        System.out.print("\rjust a sec" + state + "        ");
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }
    
    System.out.println("\r                                  \n");
    
    System.out.println(AnsiColorsUtil.WHITE.getCode());
  }
}
