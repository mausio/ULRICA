package controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import utils.generalUtils.AnsiColorsUtil;

public class LoadingScreenAnimationController implements Runnable {
  private static final String[] loadingStates = {"   ", ".  ", ".. ", "..."};
  private volatile boolean isTaskComplete = false;
  private ScheduledExecutorService executor;
  
  private void updateLoadingAnimation() {
    AtomicInteger stateIndex = new AtomicInteger(0);
    executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(() -> {
      if (!isTaskComplete) {
        System.out.print("\rjust a sec" + loadingStates[stateIndex.get()] + "        ");
        stateIndex.set((stateIndex.get() + 1) % loadingStates.length);
      } else {
        executor.shutdown();
      }
    }, 0, 500, TimeUnit.MILLISECONDS);
  }
  
  private static void setColorBeforeLoading() {
    System.out.println(AnsiColorsUtil.DARK_GRAY.getCode());
  }
  
  private static void cleanAfterLoading() {
    System.out.print("\r                    \n");
    System.out.print(AnsiColorsUtil.WHITE.getCode());
  }
  
  public static void runForNSeconds(Double durationInSeconds) {
    LoadingScreenAnimationController controller = new LoadingScreenAnimationController();
    controller.run();
    try {
      Thread.sleep((long)(durationInSeconds * 1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    controller.stop();
    cleanAfterLoading();
  }
  
  public void stop() {
    this.isTaskComplete = true;
    if (executor != null) {
      executor.shutdown();
    }
  }
  
  @Override 
  public void run() {
    setColorBeforeLoading();
    updateLoadingAnimation();
  }
}
