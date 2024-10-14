package utils.carProfileUtils;

import exception.LoadingException;
import utils.generalUtils.LoadingScreenAnimationUtil;

public class CarProfileThreadsUtil {
  
  public static void startLoadingThreads(
      Thread loadingAnimationThread, Thread loadingJsonThread) {
    loadingAnimationThread.start();
    loadingJsonThread.start();
  }
  
  public static void stopLoadingThreads(Thread loadingAnimationThread,
                                        Thread loadingJsonThread,
                                        LoadingScreenAnimationUtil loadingScreenAnimation)
      throws LoadingException {
    try {
      loadingJsonThread.join();
      loadingScreenAnimation.stop();
      loadingAnimationThread.interrupt();
      loadingAnimationThread.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new LoadingException(e.getMessage());
    }
  }
}
