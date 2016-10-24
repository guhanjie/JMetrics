/**
 * Project Name: jmetric
 * Package Name: jmetric
 * File Name: MyComponentSink.java
 * Create Date: 2016年8月1日 下午2:43:42
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.pinganfu.owl.metrics.demo;

import org.apache.commons.configuration.SubsetConfiguration;

import com.pinganfu.owl.metrics.MetricsRecord;
import com.pinganfu.owl.metrics.MetricsSink;
import com.pinganfu.owl.metrics.MetricsSystem;

/**
 * Class Name: MyComponentSink<br/>
 * Description: [description]
 * 
 * @time 2016年8月1日 下午2:43:42
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public class MetricsSinkCustom implements MetricsSink {

	@Override
	public void init(SubsetConfiguration conf) {
		
	}
	
	@Override
	public void putMetrics(MetricsRecord record) {
		System.out.println("===" + record);
	}
	
	
	public void flush() {
	}
	
	// Recommended helper method
	public MetricsSinkCustom registerWith(MetricsSystem ms) {
		return ms.register("MyCustomSink", "MyCustomSink plugin description", this);
	}
}