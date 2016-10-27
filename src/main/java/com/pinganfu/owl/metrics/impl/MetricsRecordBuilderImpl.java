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

package com.pinganfu.owl.metrics.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.pinganfu.owl.metrics.AbstractMetric;
import com.pinganfu.owl.metrics.MetricsCollector;
import com.pinganfu.owl.metrics.MetricsFilter;
import com.pinganfu.owl.metrics.MetricsInfo;
import com.pinganfu.owl.metrics.MetricsRecordBuilder;
import com.pinganfu.owl.metrics.MetricsTag;
import com.pinganfu.owl.metrics.lib.Interns;

class MetricsRecordBuilderImpl extends MetricsRecordBuilder {
  private final MetricsCollector parent;
  private final long timestamp;
  private final MetricsInfo recInfo;
  private final List<AbstractMetric> metrics;
  private final List<MetricsTag> tags;
  private final MetricsFilter recordFilter, metricFilter;
  private final boolean acceptable;

  MetricsRecordBuilderImpl(MetricsCollector parent, MetricsInfo info,
                           MetricsFilter rf, MetricsFilter mf,
                           boolean acceptable) {
    this.parent = parent;
    timestamp = System.currentTimeMillis();
    recInfo = info;
    metrics = Lists.newArrayList();
    tags = Lists.newArrayList();
    recordFilter = rf;
    metricFilter = mf;
    this.acceptable = acceptable;
  }

  @Override
  public MetricsCollector parent() { return parent; }

  @Override
  public MetricsRecordBuilderImpl tag(MetricsInfo info, String value) {
    if (acceptable) {
      tags.add(Interns.tag(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl add(MetricsTag tag) {
    tags.add(tag);
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl add(AbstractMetric metric) {
    metrics.add(metric);
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addCounter(MetricsInfo info, int value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricCounterInt(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addCounter(MetricsInfo info, long value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricCounterLong(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addGauge(MetricsInfo info, int value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricGaugeInt(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addGauge(MetricsInfo info, long value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricGaugeLong(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addGauge(MetricsInfo info, float value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricGaugeFloat(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl addGauge(MetricsInfo info, double value) {
    if (acceptable && (metricFilter == null ||
        metricFilter.accepts(info.name()))) {
      metrics.add(new MetricGaugeDouble(info, value));
    }
    return this;
  }

  @Override
  public MetricsRecordBuilderImpl setContext(String value) {
    return tag(MsInfo.Context, value);
  }

  public MetricsRecordImpl getRecord() {
    if (acceptable && (recordFilter == null || recordFilter.accepts(tags))) {
      return new MetricsRecordImpl(recInfo, timestamp, tags(), metrics());
    }
    return null;
  }

  List<MetricsTag> tags() {
    return Collections.unmodifiableList(tags);
  }

  List<AbstractMetric> metrics() {
    return Collections.unmodifiableList(metrics);
  }
}
