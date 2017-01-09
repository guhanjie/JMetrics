/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pinganfu.owl.metrics.lib;

import static com.pinganfu.owl.metrics.lib.Interns.*;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

import com.google.common.annotations.VisibleForTesting;
import com.pinganfu.owl.metrics.MetricsInfo;
import com.pinganfu.owl.metrics.MetricsRecordBuilder;
import com.pinganfu.owl.metrics.util.Quantile;
import com.pinganfu.owl.metrics.util.SampleQuantiles;
import com.pinganfu.owl.metrics.util.SampleStat;

/**
 * A mutable metric with stats.
 *
 * Useful for keeping throughput/latency stats.
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class MutableStat extends MutableMetric {
  private final MetricsInfo numInfo;
  private final MetricsInfo avgInfo;
  private final MetricsInfo stdevInfo;
  private final MetricsInfo minInfo;
  private final MetricsInfo maxInfo;
  private final MetricsInfo iNumInfo;
  private final MetricsInfo iAvgInfo;
  private final MetricsInfo iStdevInfo;
  private final MetricsInfo iMinInfo;
  private final MetricsInfo iMaxInfo;
  private final MetricsInfo quantileNumInfo;
  private final MetricsInfo[] quantileInfos;

  private boolean extended = false;

  private final SampleStat intervalStat = new SampleStat();
  //private final SampleStat prevStat = new SampleStat();
  private final SampleStat wholeStat = new SampleStat();
  
  @VisibleForTesting
  public static final Quantile[] quantiles = { new Quantile(0.50, 0.050),
      new Quantile(0.75, 0.025), new Quantile(0.90, 0.010),
      new Quantile(0.95, 0.005), new Quantile(0.99, 0.001) };

  private SampleQuantiles estimator;
  private long prevQuantilesCount = 0;
  private Map<Quantile, Long> prevQuantilesSnapshot = null;

  /**
   * Construct a sample statistics metric
   * @param name        of the metric
   * @param description of the metric
   * @param sampleName  of the metric (e.g. "Ops")
   * @param valueName   of the metric (e.g. "Time", "Latency")
   * @param extended    create extended stats (stdev, min/max etc.) by default.
   */
  public MutableStat(String name, String description,
                     String sampleName, String valueName, boolean extended) {
    String ucName = StringUtils.capitalize(name);
    String usName = StringUtils.capitalize(sampleName);
    String uvName = StringUtils.capitalize(valueName);
    String desc = StringUtils.uncapitalize(description);
    String lsName = StringUtils.uncapitalize(sampleName);
    String lvName = StringUtils.uncapitalize(valueName);
    //whole statistics info
    numInfo = info(ucName +"Num"+ usName, "Number of "+ lsName +" for "+ desc);
    avgInfo = info(ucName +"Avg"+ uvName, "Average "+ lvName +" for "+ desc);
    stdevInfo = info(ucName +"Stdev"+ uvName, "Standard deviation of "+ lvName +" for "+ desc);
    minInfo = info(ucName +"Min"+ uvName, "Min "+ lvName +" for "+ desc);
    maxInfo = info(ucName +"Max"+ uvName, "Max "+ lvName +" for "+ desc);
	//interval statistics info
    iNumInfo = info(ucName + "IntervalNum" + usName, String.format(
    		        "Interval number of %s for %s in the interval", lsName, desc));
    iAvgInfo = info(ucName +"IntervalAvg"+ uvName, String.format(
    		        "Interval average of %s for %s in the interval", lvName, desc));
    iStdevInfo = info(ucName +"IntervalStdev"+ uvName, String.format(
    		        "Interval standard deviation of %s for %s in the interval", lvName, desc));
    iMinInfo = info(ucName +"IntervalMin"+ uvName, String.format(
    		        "Interval min of %s for %s in the interval", lvName, desc));
    iMaxInfo = info(ucName + "IntervalMax"+ uvName, String.format(
    		        "Interval max of %s for %s in the interval", lvName, desc));
    this.extended = extended;
    // interval quantiles info
    quantileNumInfo = info(ucName + "QuantilesNum" + usName, String.format(
    				"Quantiles Samples Number of %s for %s in the interval", lsName, desc));
    quantileInfos = new MetricsInfo[quantiles.length];
    String nameTemplate = ucName + "%dthPercentile" + uvName;
    String descTemplate = "%d percentile " + lvName + " for " + desc +"in the interval";
    for (int i = 0; i < quantiles.length; i++) {
      int percentile = (int) (100 * quantiles[i].quantile);
      quantileInfos[i] = info(String.format(nameTemplate, percentile),
          String.format(descTemplate, percentile));
    }
    estimator = new SampleQuantiles(quantiles);
  }

  /**
   * Construct a snapshot stat metric with extended stat off by default
   * @param name        of the metric
   * @param description of the metric
   * @param sampleName  of the metric (e.g. "Ops")
   * @param valueName   of the metric (e.g. "Time", "Latency")
   */
  public MutableStat(String name, String description,
                     String sampleName, String valueName) {
    this(name, description, sampleName, valueName, false);
  }

  /**
   * Set whether to display the extended stats (stdev, min/max, quantiles etc.) or not
   * @param extended enable/disable displaying extended stats
   */
  public synchronized void setExtended(boolean extended) {
    this.extended = extended;
  }

  /**
   * Add a number of samples and their sum to the running stat
   * @param numSamples  number of samples
   * @param sum of the samples
   */
  public synchronized void add(long numSamples, long sum) {
	wholeStat.add(numSamples, sum);
    intervalStat.add(numSamples, sum);
    setChanged();
  }

  /**
   * Add a snapshot to the metric
   * @param value of the metric
   */
  public synchronized void add(long value) {
	wholeStat.add(value);
    intervalStat.add(value);
    estimator.insert(value);
    setChanged();
  }

  public synchronized void snapshot(MetricsRecordBuilder builder, boolean all) {
    if (all || changed()) {
      builder.addCounter(numInfo, wholeStat.numSamples())
             .addGauge(avgInfo, wholeStat.mean())
             .addCounter(iNumInfo, intervalStat.numSamples())
             .addGauge(iAvgInfo, intervalStat.mean());
      if (extended) {
        builder.addGauge(stdevInfo, wholeStat.stddev())
		       .addGauge(minInfo, wholeStat.min())
		       .addGauge(maxInfo, wholeStat.max())
		       .addGauge(iStdevInfo, intervalStat.stddev())
               .addGauge(iMinInfo, intervalStat.min())
               .addGauge(iMaxInfo, intervalStat.max());

        builder.addGauge(quantileNumInfo, prevQuantilesCount);
        for (int i = 0; i < quantiles.length; i++) {
          long newValue = 0;
          // If snapshot is null, we failed to update since the window was empty
          if (prevQuantilesSnapshot != null) {
            newValue = prevQuantilesSnapshot.get(quantiles[i]);
          }
          builder.addGauge(quantileInfos[i], newValue);
        }
      }
      if (changed()) {		//roll over for interval
        if (intervalStat.numSamples() > 0) {
//          intervalStat.copyTo(prevStat);
          intervalStat.reset();
        }
        if(estimator.getCount()>0) {
            prevQuantilesCount = estimator.getCount();
            prevQuantilesSnapshot = estimator.snapshot();
            estimator.clear();
        }
        clearChanged();
      }
    }
  }

//  private SampleStat lastStat() {
//	  if(changed()) {
//          intervalStat.copyTo(prevStat);
//	  }
//	  if(intervalStat.numSamples() == 0) {
//		  prevStat.reset();
//	  }
//	  return prevStat;
//    //return changed() ? intervalStat : prevStat;
//  }

}
