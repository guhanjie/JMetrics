/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.demo 
 * File Name:			MetricsSourceCustom2.java 
 * Create Date:		2016年9月9日 下午6:57:22 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import com.pinganfu.owl.metrics.MetricsCollector;
import com.pinganfu.owl.metrics.MetricsRecordBuilder;
import com.pinganfu.owl.metrics.MetricsSource;
import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.MetricsTag;
import com.pinganfu.owl.metrics.lib.Interns;
import com.pinganfu.owl.metrics.lib.MetricsRegistry;
import com.pinganfu.owl.metrics.lib.MutableCounterInt;
import com.pinganfu.owl.metrics.lib.MutableCounterLong;
import com.pinganfu.owl.metrics.lib.MutableGaugeInt;
import com.pinganfu.owl.metrics.lib.MutableGaugeLong;
import com.pinganfu.owl.metrics.lib.MutableMetric;
import com.pinganfu.owl.metrics.lib.MutableRate;
import com.pinganfu.owl.metrics.lib.MutableStat;

/**
 * Class Name:		MetricsSourceCustom2<br/>
 * Description:		[description]
 * @time				2016年9月9日 下午6:57:22
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class MetricsSourceCustom2 implements MetricsSource {

	private final MetricsRegistry registry;
	private final String context;

	public MetricsSourceCustom2(String name, String context) {
		this.registry = new MetricsRegistry(name);
		this.context = context;
	}
	
	public MutableCounterInt newCounter(String name, String desc, int iVal) {
		return registry.newCounter(Interns.info(name, desc), iVal);
	}
	
	public MutableCounterLong newCounter(String name, String desc, long iVal) {
	    return registry.newCounter(Interns.info(name, desc), iVal);
	}
	
	public MutableGaugeInt newGauge(String name, String desc, int iVal) {
	    return registry.newGauge(Interns.info(name, desc), iVal);
	}
	
	public MutableGaugeLong newGauge(String name, String desc, long iVal) {
		return registry.newGauge(Interns.info(name, desc), iVal);
	}
	
	public MutableStat newStat(String name, String desc,
                    								String sampleName, String valueName) {
		return registry.newStat(name, desc, sampleName, valueName, false);
	}
	
	public MutableRate newRate(String name, String description) {
		return registry.newRate(name, description, false);
	}
	
	public MetricsRegistry tag(String name, String description, String value) {
		return registry.tag(name, description, value, false);
	}
	
	public synchronized MutableMetric get(String name) {
		return registry.get(name);
	}

	public synchronized MetricsTag getTag(String name) {
		return registry.getTag(name);
	}
	
	@Override
	public void getMetrics(MetricsCollector collector, boolean all) {
		MetricsRecordBuilder rb = collector.addRecord(registry.info()).setContext(this.context);
		registry.snapshot(rb, all);
	}

	public MetricsSourceCustom2 registerWith(MetricsSystem ms) {
	    return ms.register(registry.info().name(), registry.info().description(), this);
	}
}
