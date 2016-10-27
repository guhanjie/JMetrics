/**
 * Project Name: jmetric
 * Package Name: jmetric
 * File Name: MyMetrics.java
 * Create Date: 2016年7月29日 下午6:13:58
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.guhanjie.jmetrics.test;

import org.apache.hadoop.metrics2.MetricsSystem;
import org.apache.hadoop.metrics2.annotation.Metric;
import org.apache.hadoop.metrics2.annotation.Metrics;
import org.apache.hadoop.metrics2.lib.MutableCounterLong;
import org.apache.hadoop.metrics2.lib.MutableGaugeInt;

// default record name is the class name
// default context name is "default"
@Metrics(context = "test")
public class MyAnnotationSource {
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
		return 42;
	}
	
	// Recommended helper method
	public MyAnnotationSource registerWith(MetricsSystem ms) {
		return ms.register("MyAnnotationSource", "MyAnnotationSource metrics description", this);
	}
}
