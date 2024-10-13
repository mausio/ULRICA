package utils;

public class LoadingScreenAnimation implements Runnable {
  private static String[] loadingStates = {"   ", ".  ", ".. ", "..."};
  private volatile boolean isTaskComplete = false;
  
  private static void loadingLoop() {
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
  
  private static void setColorBeforeLoading() {
    System.out.println(AnsiColorsUtil.DARK_GRAY.getCode());
  }
  
  private static void cleanAfterLoading() {
    System.out.print("\r                                \n");
    System.out.println(AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void runForNSeconds(Double durationInSeconds) {
    long startTime = System.currentTimeMillis();
    long endTime = (long) (startTime + (durationInSeconds * 1000));
    
    setColorBeforeLoading();
    
    while (System.currentTimeMillis() < endTime) {
      loadingLoop();
    }
    
    cleanAfterLoading();
  }
  
  public void stop() {
    this.isTaskComplete = true;
  }
  
  @Override public void run() {
    setColorBeforeLoading();
    
    while (!isTaskComplete) {
      loadingLoop();
    }
    cleanAfterLoading();
  }
}
