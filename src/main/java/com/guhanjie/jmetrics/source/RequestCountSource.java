/**
 * Project Name: JMetrics
 * Package Name: com.guhanjie.jmetrics.spi
 * File Name: RequestCountSource.java
 * Create Date: 2016年8月9日 下午4:50:27
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.guhanjie.jmetrics.source;

import static org.apache.hadoop.metrics2.lib.Interns.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.metrics2.MetricsCollector;
import org.apache.hadoop.metrics2.MetricsSource;
import org.apache.hadoop.metrics2.lib.MutableCounterLong;

/**
 * Class Name: RequestCountSource<br/>
 * Description: [description]
 * 
 * @time 2016年8月9日 下午4:50:27
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public class RequestCountSource implements MetricsSource {
	private Map<Class, MutableCounterLong> requestsCount;
	
	//Spring BeanPostProcessor
	private void init() {
		requestsCount = new HashMap<Class, MutableCounterLong>();
		//Spring上下文获取所有请求接口类，逐个添加到map
	}
	
	@Override
	public void getMetrics(MetricsCollector collector, boolean all) {
		collector.addRecord("foo")
		                .setContext("test")
		                .addGauge(info("My foo Metric", "My metric description"), 888);
		collector.addRecord("bar")
		                .setContext("test")
		                .addGauge(info("My bar Metric", "My metric description"), 999);
	}
}