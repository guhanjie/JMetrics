/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.lib 
 * File Name:			MetricsSourceBuilder.java 
 * Create Date:		2016年10月10日 下午12:22:18 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.lib;

import static com.google.common.base.Preconditions.checkNotNull;

import com.pinganfu.owl.metrics.MetricsCollector;
import com.pinganfu.owl.metrics.MetricsInfo;
import com.pinganfu.owl.metrics.MetricsSource;
import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.MetricsTag;

/**
 * Class Name:		MetricsSourceBuilder<br/>
 * Description:		[description]
 * @time				2016年10月10日 下午12:22:18
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class MetricsSourceBuilder {

	private final MetricsSystem ms;
	private final String context;
	private final MetricsInfo info;
	private final MetricsRegistry registry;

	public MetricsSourceBuilder(MetricsSystem ms, String context, String sourcename) {
		checkNotNull(ms, "metrics system");
		checkNotNull(context, "context");
		checkNotNull(sourcename, "metrics source name");
		this.ms = ms;
		this.context = context;
	    this.info = Interns.info(sourcename, sourcename);
		this.registry = new MetricsRegistry(info).setContext(context);
	}
	
	public MetricsSource build() {
		//build a metrics source from metrics registry
	    MetricsSource source = new MetricsSource() {
	        @Override
	        public void getMetrics(MetricsCollector builder, boolean all) {
	            registry.snapshot(builder.addRecord(info).setContext(context), all);
	        }
	    };
	    //register metrics source into metrics system
	    ms.register(info.name(), info.description(), source);
	    return source;
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
	
	public MutableQuantiles newQuantiles(String name, String desc,
				      						String sampleName, String valueName, int interval) {
		return registry.newQuantiles(name, desc, sampleName, valueName, interval);
	}
	
	public MutableStat newStat(String name, String desc, String sampleName, String valueName) {
		return registry.newStat(name, desc, sampleName, valueName, false);
	}
	
	public MutableRate newRate(String name, String description) {
		return registry.newRate(name, description, false);
	}
	
	public MetricsRegistry tag(String name, String description, String value) {
		return registry.tag(name, description, value, false);
	}
	
	public synchronized MutableMetric getMetric(String name) {
		return registry.get(name);
	}

	public synchronized MetricsTag getTag(String name) {
		return registry.getTag(name);
	}
}
