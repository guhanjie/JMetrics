/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			MyMetricsSource.java 
 * Create Date:		2016年8月1日 上午10:47:18 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.jmetrics.test;

import static org.apache.hadoop.metrics2.lib.Interns.info;

import org.apache.hadoop.metrics2.MetricsCollector;
import org.apache.hadoop.metrics2.MetricsSource;
import org.apache.hadoop.metrics2.MetricsSystem;

/**
 * Class Name:		MyMetricsSource<br/>
 * Description:		[description]
 * @time				2016年8月1日 上午10:47:18
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class MyCustomSource implements MetricsSource {

	  @Override
	  public void getMetrics(MetricsCollector collector, boolean all) {
		collector.addRecord("foo")
			.setContext("test")
			.addGauge(info("My foo Metric", "My metric description"), 888);
		collector.addRecord("bar")
		.setContext("test")
		.addGauge(info("My bar Metric", "My metric description"), 999);
//		  collector.addRecord("foo")
//	      .addGauge(new MetricGaugeLong(new MetricsInfoImpl("g0", "an integer gauge"), 42L))
//	      .addCounter("c0", "a long counter", 42L);
//
//	    // Typical merics soturces generate one record per snapshot.
//	    // We can add more records, which is not supported by annotations.
//	    builder.addRecord("bar")
//	      .addGauge("g1", "a float gauge", 42.0)
//	      .addCounter("c1", "a integer counter", 42);
	  }

	  public MyCustomSource registerWith(MetricsSystem ms) {
	    return ms.register("MyCustomMetrics", "MyCustomMetrics description", this);
	  }
	}