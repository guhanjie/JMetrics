/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			MyMetricsSource.java 
 * Create Date:		2016年8月1日 上午10:47:18 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import static com.pinganfu.owl.metrics.lib.Interns.info;

import java.util.Random;

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
public class MetricsSourceCustom1 implements MetricsSource {
	
	  @Override
	  public void getMetrics(MetricsCollector collector, boolean all) {
	    // Typical metrics sources generate one record per snapshot.
	    // We can add more records, which is not supported by annotations.
		Random random = new Random();
		collector.addRecord("record1").setContext("context1")
			.addGauge(info("heap", "heap size"), random.nextInt(1000));
		
		collector.addRecord("record2").setContext("test")
			.addGauge(info("stack", "stack size"), random.nextInt(100));
		
		collector.addRecord("record3").setContext("test1")
			.addGauge(info("heap", "heap size"), random.nextInt(1000))
			.addGauge(info("stack", "stack size"), random.nextInt(100))
			.addCounter(info("throughput", "a method invoked counts"), 1L);

	  }

	  public MetricsSourceCustom1 registerWith(MetricsSystem ms) {
	    return ms.register("CustomMetrics", "CustomMetrics description", this);
	  }
	}