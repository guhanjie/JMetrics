/**
 * Project Name: jmetric
 * Package Name: jmetric
 * File Name: MyMetrics.java
 * Create Date: 2016年7月29日 下午6:13:58
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.pinganfu.owl.metrics.demo;

import java.util.Random;

import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.annotation.Metric;
import com.pinganfu.owl.metrics.annotation.Metrics;
import com.pinganfu.owl.metrics.lib.MutableCounterLong;
import com.pinganfu.owl.metrics.lib.MutableGaugeInt;

// default record name is the class name
// default context name is "default"
@Metrics(context = "test")
public class MetricsSourceAnnotation {
	
	// Default metric name is the variable name
	@Metric("An integer gauge")
	MutableGaugeInt		g1;
	
	// Default type is inferred from the mutable metric type
	@Metric("An long integer counter")
	MutableCounterLong	c1;
	
	// Default name of metric is method name sans get
	// Default type of metric is gauge
	@Metric("An integer gauge named MyMetric")
	public int getMyMetric() {
		return new Random().nextInt(100);
	}
	
	// Recommended helper method to register to metrics system
	public MetricsSourceAnnotation registerWith(MetricsSystem ms) {
		return ms.register("MyAnnotationSource", "MyAnnotationSource metrics description", this);
	}
}
