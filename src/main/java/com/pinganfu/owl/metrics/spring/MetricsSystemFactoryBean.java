/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.spring 
 * File Name:			MetricsSystemFactoryBean.java 
 * Create Date:		2016年11月9日 下午4:43:30 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.spring;

import java.lang.management.ManagementFactory;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.lib.DefaultMetricsSystem;
import com.pinganfu.owl.metrics.source.JvmMetrics;

/**
 * Class Name:		MetricsSystemFactoryBean<br/>
 * Description:		[description]
 * Time:					2016年11月9日 下午4:43:30
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class MetricsSystemFactoryBean extends AbstractFactoryBean<MetricsSystem> {

	private static final Log LOG = LogFactory.getLog(MetricsSystemFactoryBean.class);
	
	private String context;
	private boolean enableJVM;
	private Map<String, Object> metricsSources;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public Class<?> getObjectType() {
		return MetricsSystem.class;
	}

	@Override
	protected MetricsSystem createInstance() throws Exception {
		LOG.info("starting metrics system in spring context...");
		if(context == null) {
			LOG.warn("metrics system configuration not specified, "
							+ "using default configuration file: owl-metrics.properties.");
			context = "";
		}
		MetricsSystem ms = DefaultMetricsSystem.initialize(context);
		if(enableJVM) {
			String name = ManagementFactory.getRuntimeMXBean().getName();
			JvmMetrics.create(name.split("@")[0], name, ms);
		}
		if(metricsSources != null) {
			for(String sourceName : metricsSources.keySet()) {
				Object metricsSource = metricsSources.get(sourceName);
				ms.register(sourceName, sourceName, metricsSource);
			}
		}
		return ms;
	}
		
	@Override
	protected void destroyInstance(MetricsSystem ms) throws Exception {
		LOG.info("shutdown metrics system on spring context close or stop or destroy...");
		ms.shutdown();
	}

	public boolean isEnableJVM() {
		return enableJVM;
	}

	public void setEnableJVM(boolean enableJVM) {
		this.enableJVM = enableJVM;
	}

	public Map<String, Object> getMetricsSources() {
		return metricsSources;
	}

	public void setMetricsSources(Map<String, Object> metricsSources) {
		this.metricsSources = metricsSources;
	}

}
