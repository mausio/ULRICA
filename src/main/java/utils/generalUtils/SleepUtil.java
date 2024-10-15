package utils.generalUtils;

public class SleepUtil {
  public  void waitForFSeconds(Double seconds) {
    try {
      Thread.sleep((long) (seconds * 1000));
    } catch (InterruptedException e) {
      System.err.println("Something went wrong: " + e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
