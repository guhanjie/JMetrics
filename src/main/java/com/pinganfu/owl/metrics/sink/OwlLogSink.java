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

package com.pinganfu.owl.metrics.sink;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinganfu.owl.metrics.MetricsException;
import com.pinganfu.owl.metrics.MetricsRecord;
import com.pinganfu.owl.metrics.MetricsSink;

/**
 * A metrics sink that writes to a file
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class OwlLogSink implements MetricsSink, Closeable {
	
  private static final String APPENDER_NAME = "appender";
  private static final String DEFAULT_APPENDER_NAME = "owl";
	
  private static Logger LOG;
  
  @Override
  public void init(SubsetConfiguration conf) {
    String appender = conf.getString(APPENDER_NAME);
    if(appender == null) {
    	appender = DEFAULT_APPENDER_NAME;
    }
    try {
    	LOG = LoggerFactory.getLogger(appender);
    } catch (Exception e) {
      throw new MetricsException("Error creating log appender:"+ appender, e);
    }
  }

  @Override
  public void putMetrics(MetricsRecord record) {
	LOG.info("metric", record);
  }

  @Override
  public void flush() {
	  
  }

  @Override
  public void close() throws IOException {
	  
  }
}
