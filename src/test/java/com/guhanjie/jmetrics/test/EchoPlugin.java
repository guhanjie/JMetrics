/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			EchoPlugin.java 
 * Create Date:		2016年8月1日 上午11:07:43 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.jmetrics.test;

import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.hadoop.metrics2.MetricsInfo;
import org.apache.hadoop.metrics2.MetricsRecord;
import org.apache.hadoop.metrics2.MetricsSink;
import org.apache.hadoop.metrics2.MetricsVisitor;

/**
 * Class Name:		EchoPlugin<br/>
 * Description:		[description]
 * @time				2016年8月1日 上午11:07:43
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class EchoPlugin implements MetricsSink, MetricsVisitor {

	  @Override // MetricsPlugin
	  public void init(SubsetConfiguration conf) {
	    // do plugin specific initialization here
	  }

	  @Override // MetricsSink
	  public void putMetrics(MetricsRecord rec) {
//	    echoHeader(rec.name(), rec.context());
//
//	    for (MetricTag tag : rec.tags())
//	      echoTag(tag.getName(), tag.getValue());
//	  
//	    for (AbstractMetric metric : rec.metrics())
//	      metric.visit(this);
	  }

	  @Override // MetricsSink
	  public void flush() {
	    // do sink specific buffer management here
	  }

	  @Override // MetricsVisitor
	  public void counter(MetricsInfo info, int value) {
	    //echoCounterInt32(info.name(), value);
	  }

	  @Override // MetricsVisitor
	  public void counter(MetricsInfo info, long value) {
	    //echoCounterInt64(info.name(), value);
	  }

	  @Override // MetricsVisitor
	  public void gauge(MetricsInfo info, int value) {
	    //echoGaugeInt32(info.name(), value);
	  }

	  @Override // MetricsVisitor
	  public void gauge(MetricsInfo info, long value) {
	    //echoGaugeInt64(info.name(), value);
	  }

	  @Override // MetricsVisitor
	  public void gauge(MetricsInfo info, float value) {
	    //echoGaugeFp32(info.name(), value);
	  }

	  @Override // MetricsVisitor
	  public void gauge(MetricsInfo info, double value) {
	    //echoGaugeFp64(info.name(), value);
	  }
	}