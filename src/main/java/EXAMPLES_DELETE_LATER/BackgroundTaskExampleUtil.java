package EXAMPLES_DELETE_LATER;

import utils.generalUtils.LoadingScreenAnimationUtil;

public class BackgroundTaskExampleUtil implements Runnable {
  private final LoadingScreenAnimationUtil loadingScreenAnimation;
  
  public BackgroundTaskExampleUtil(
      LoadingScreenAnimationUtil loadingScreenAnimation) {
    this.loadingScreenAnimation = loadingScreenAnimation;
  }
  
  @Override
  public void run() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    
    System.out.println("\nBackground task complete!");
    
    loadingScreenAnimation.stop();
  }
  
}
