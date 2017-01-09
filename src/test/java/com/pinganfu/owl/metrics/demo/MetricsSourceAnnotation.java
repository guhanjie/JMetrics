/**
 * Project Name: jmetric
 * Package Name: jmetric
 * File Name: MyMetrics.java
 * Create Date: 2016年7月29日 下午6:13:58
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */
package com.pinganfu.owl.metrics.demo;

import java.util.Random;

import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.annotation.Metric;
import com.pinganfu.owl.metrics.annotation.Metrics;
import com.pinganfu.owl.metrics.lib.MutableCounterLong;
import com.pinganfu.owl.metrics.lib.MutableGaugeInt;
import com.pinganfu.owl.metrics.lib.MutableRate;

// default record name is the class name
// default context name is "default"
@Metrics(context = "test")
public class MetricsSourceAnnotation {
	
	// Default metric name is the variable name
	@Metric(value={"gauge", "a variable metric that changes at any moment"})
	MutableGaugeInt		g1;
	
	// Default type is inferred from the mutable metric type
	@Metric(value={"counter", "a counter metric that always increasing"})
	MutableCounterLong	c1;
	
	@Metric(value={"rate", "throughput rate for some method"})
	MutableRate 	rate1;
	
	// Default name of metric is method name sans get
	// Default type of metric is gauge
	@Metric("success")
	public int successed() {
		rate1.add(100);
		return new Random().nextInt(100);
	}
	
	@Metric("fail")
	public int failed() {
		return new Random().nextInt(100);
	}
	
	// Recommended helper method to register to metrics system
	public MetricsSourceAnnotation registerWith(MetricsSystem ms) {
		return ms.register("MyAnnotationSource", "MyAnnotationSource metrics description", this);
	}
}
