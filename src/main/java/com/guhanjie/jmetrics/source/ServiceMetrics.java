/**
 * Project Name: JMetrics
 * Package Name: com.guhanjie.jmetrics.spi
 * File Name: ServiceMetrics.java
 * Create Date: 2016年8月12日 下午3:55:29
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.guhanjie.jmetrics.source;

import static com.guhanjie.jmetrics.source.ServiceMetricsInfo.*;
import static org.apache.hadoop.metrics2.lib.Interns.*;
import org.apache.hadoop.metrics2.MetricsCollector;
import org.apache.hadoop.metrics2.MetricsRecordBuilder;
import org.apache.hadoop.metrics2.MetricsSource;
import org.apache.hadoop.metrics2.MetricsSystem;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;

/**
 * Class Name: ServiceMetrics<br/>
 * Description: [Service related metrics source implemation]
 * 
 * @time 2016年8月12日 下午3:55:29
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public class ServiceMetrics implements MetricsSource {
	final String	serviceName;
	final Class		serviceClass;
	
	public ServiceMetrics(String serviceName, Class serviceClass) {
		this.serviceName = serviceName;
		this.serviceClass = serviceClass;
		init(DefaultMetricsSystem.instance());
	}
	
	public ServiceMetrics(String serviceName, Class serviceClass, MetricsSystem ms) {
		if(ms == null) {
			ms = DefaultMetricsSystem.instance();
		}
		this.serviceName = serviceName;
		this.serviceClass = serviceClass;
		init(ms);
	}
	
	private ServiceMetrics init(MetricsSystem ms) {
		return ms.register(ServiceMetrics.name(), ServiceMetrics.description(),
		                this);
	}
	
	@Override
	public void getMetrics(MetricsCollector collector, boolean all) {
		MetricsRecordBuilder rb = collector.addRecord(ServiceMetrics)
		                .setContext("service").tag(ServiceMetrics, "")
		                .tag(ServiceMetrics, "");
	}
}
