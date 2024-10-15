package EXAMPLES_DELETE_LATER;

import controller.LoadingScreenAnimationController;

public class BackgroundTaskExampleUtil implements Runnable {
  private final LoadingScreenAnimationController loadingScreenAnimation;
  
  public BackgroundTaskExampleUtil(
      LoadingScreenAnimationController loadingScreenAnimation) {
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
