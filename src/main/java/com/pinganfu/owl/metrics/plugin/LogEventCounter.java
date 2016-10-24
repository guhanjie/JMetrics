/** 
 * Project Name:		owl.metrics 
 * Package Name:	org.apache.hadoop.metrics2.plugin 
 * File Name:			EventCounter.java 
 * Create Date:		2016年10月9日 下午3:25:08 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.plugin;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

/**
 * A log4J Appender that simply counts logging events in three levels:
 * fatal, error and warn. The class name is used in log4j.properties
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class LogEventCounter extends AppenderSkeleton {
  private static final int FATAL = 0;
  private static final int ERROR = 1;
  private static final int WARN = 2;
  private static final int INFO = 3;

  private static class EventCounts {
    private final AtomicLong[] counts = { new AtomicLong(0), 
											    				new AtomicLong(0), 
											    				new AtomicLong(0), 
											    				new AtomicLong(0) };

    private synchronized void incr(int i) {
      counts[i].incrementAndGet();
    }

    private synchronized long get(int i) {
      return counts[i].get();
    }
  }

  private static EventCounts counts = new EventCounts();

  @InterfaceAudience.Private
  public static long getFatal() {
    return counts.get(FATAL);
  }

  @InterfaceAudience.Private
  public static long getError() {
    return counts.get(ERROR);
  }

  @InterfaceAudience.Private
  public static long getWarn() {
    return counts.get(WARN);
  }

  @InterfaceAudience.Private
  public static long getInfo() {
    return counts.get(INFO);
  }

  @Override
  public void append(LoggingEvent event) {
    Level level = event.getLevel();
    // depends on the api, == might not work
    // see HADOOP-7055 for details
    if (level.equals(Level.INFO)) {
      counts.incr(INFO);
    }
    else if (level.equals(Level.WARN)) {
      counts.incr(WARN);
    }
    else if (level.equals(Level.ERROR)) {
      counts.incr(ERROR);
    }
    else if (level.equals(Level.FATAL)) {
      counts.incr(FATAL);
    }
  }

  @Override
  public void close() {
  }

  @Override
  public boolean requiresLayout() {
    return false;
  }
}
