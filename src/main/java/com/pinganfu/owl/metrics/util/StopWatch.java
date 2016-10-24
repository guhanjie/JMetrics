/** 
 * Project Name:		owl.metrics 
 * Package Name:	org.apache.hadoop.metrics2.util 
 * File Name:			StopWatch.java 
 * Create Date:		2016年10月9日 下午2:59:08 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.util;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

/**
 * A simplified StopWatch implementation which can measure times in nanoseconds.
 */
public class StopWatch implements Closeable {
  private boolean isStarted;
  private long startNanos;
  private long currentElapsedNanos;

  public StopWatch() {
  }

  /**
   * The method is used to find out if the StopWatch is started.
   * @return boolean If the StopWatch is started.
   */
  public boolean isRunning() {
    return isStarted;
  }

  /**
   * Start to measure times and make the state of stopwatch running.
   * @return this instance of StopWatch.
   */
  public StopWatch start() {
    if (isStarted) {
      throw new IllegalStateException("StopWatch is already running");
    }
    isStarted = true;
    startNanos = System.nanoTime();
    return this;
  }

  /**
   * Stop elapsed time and make the state of stopwatch stop.
   * @return this instance of StopWatch.
   */
  public StopWatch stop() {
    if (!isStarted) {
      throw new IllegalStateException("StopWatch is already stopped");
    }
    long now = System.nanoTime();
    isStarted = false;
    currentElapsedNanos += now - startNanos;
    return this;
  }

  /**
   * Reset elapsed time to zero and make the state of stopwatch stop.
   * @return this instance of StopWatch.
   */
  public StopWatch reset() {
    currentElapsedNanos = 0;
    isStarted = false;
    return this;
  }

  /**
   * @return current elapsed time in specified timeunit.
   */
  public long now(TimeUnit timeUnit) {
    return timeUnit.convert(now(), TimeUnit.NANOSECONDS);

  }

  /**
   * @return current elapsed time in nanosecond.
   */
  public long now() {
    return isStarted ?
        System.nanoTime() - startNanos + currentElapsedNanos :
        currentElapsedNanos;
  }

  @Override
  public String toString() {
    return String.valueOf(now());
  }

  @Override
  public void close() {
    if (isStarted) {
      stop();
    }
  }
}
