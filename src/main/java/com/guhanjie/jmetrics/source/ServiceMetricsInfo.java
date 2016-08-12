/**
 * Project Name: JMetrics
 * Package Name: com.guhanjie.jmetrics.spi
 * File Name: ServiceMetricsInfo.java
 * Create Date: 2016年8月11日 下午6:20:01
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
package com.guhanjie.jmetrics.source;

import org.apache.hadoop.metrics2.MetricsInfo;

import com.google.common.base.Objects;

/**
 * Class Name: ServiceMetricsInfo<br/>
 * Description: [Service related metrics info instances]
 * 
 * @time 2016年8月11日 下午6:20:01
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public enum ServiceMetricsInfo implements MetricsInfo {
	ServiceMetrics("Service related metrics etc."), // record info
    // ============metrics============
    // Request
	RequestCount("Total request count"),	// 服务请求数
	RequestLatency("Latency of processing request in mili seconds"),	// 请求延时（包含平均、最大、最小处理时间）
	RequestSuccesses("Number of request sucesses"),	// 请求成功数
	RequestFailures("Number of request failures"),	// 请求失败数
	RequestTimeoutCount("Number of request timeout"),	// 请求超时次数
	;
	private final String desc;
	
	ServiceMetricsInfo(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String description() {
		return desc;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		                .add("name", name()).add("description", desc)
		                .toString();
	}
}
