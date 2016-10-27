/*
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

import org.apache.hadoop.classification.InterfaceAudience;

import com.pinganfu.owl.metrics.MetricsException;

/**
 * Experimental interface to extend metrics dynamically
 */
@InterfaceAudience.Private
public enum DefaultMetricsFactory {
  INSTANCE; // the singleton

  private MutableMetricFactory mmfImpl;

  public static MutableMetricFactory getMutableMetricsFactory() {
    return INSTANCE.getInstance(MutableMetricFactory.class);
  }

  @SuppressWarnings("unchecked")
  public synchronized <T> T getInstance(Class<T> cls) {
    if (cls == MutableMetricFactory.class) {
      if (mmfImpl == null) {
        mmfImpl = new MutableMetricFactory();
      }
      return (T) mmfImpl;
    }
    throw new MetricsException("Unknown metrics factory type: "+ cls.getName());
  }

  public synchronized void setInstance(MutableMetricFactory factory) {
    mmfImpl = factory;
  }
}
