/**
 * Project Name: jmetric
 * Package Name: jmetric
 * File Name: MyComponentSink.java
 * Create Date: 2016年8月1日 下午2:43:42
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.guhanjie.jmetrics.core;

import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.hadoop.metrics2.MetricsRecord;
import org.apache.hadoop.metrics2.MetricsSink;
import org.apache.hadoop.metrics2.MetricsSystem;

/**
 * Class Name: MyComponentSink<br/>
 * Description: [description]
 * 
 * @time 2016年8月1日 下午2:43:42
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public class MyCustomSink implements MetricsSink {
	public void putMetrics(MetricsRecord record) {
		System.out.println("===" + record);
	}
	
	public void init(SubsetConfiguration conf) {
	}
	
	public void flush() {
	}
	
	// Recommended helper method
	public MyCustomSink registerWith(MetricsSystem ms) {
		return ms.register("MyCustomSink", "MyCustomSink plugin description", this);
	}
}