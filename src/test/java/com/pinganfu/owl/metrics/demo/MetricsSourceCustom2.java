/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			MyMetricsSource.java 
 * Create Date:		2016年8月1日 上午10:47:18 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import static com.pinganfu.owl.metrics.lib.Interns.info;

import com.pinganfu.owl.metrics.MetricsCollector;
import com.pinganfu.owl.metrics.MetricsSource;
import com.pinganfu.owl.metrics.MetricsSystem;

/**
 * Class Name:		MyMetricsSource<br/>
 * Description:		[description]
 * @time				2016年8月1日 上午10:47:18
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class MetricsSourceCustom2 implements MetricsSource {
	
	  @Override
	  public void getMetrics(MetricsCollector collector, boolean all) {
		collector.addRecord("foo").setContext("test")
			.addGauge(info("My foo Metric", "My metric description"), 888);
		
		collector.addRecord("bar").setContext("test")
			.addGauge(info("My bar Metric", "My metric description"), 999);
		
		collector.addRecord("www").setContext("test1")
			.addGauge(info("g0", "an integer gauge"), 0L)
			.addCounter(info("c0", "a long counter"), 42L);

//	    // Typical metrics sources generate one record per snapshot.
//	    // We can add more records, which is not supported by annotations.
//	    builder.addRecord("bar")
//	      .addGauge("g1", "a float gauge", 42.0)
//	      .addCounter("c1", "a integer counter", 42);
	  }

	  public MetricsSourceCustom2 registerWith(MetricsSystem ms) {
	    return ms.register("MyCustomMetrics", "MyCustomMetrics description", this);
	  }
	}