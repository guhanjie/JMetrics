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
 * Class Name: CacheMetricsInfo<br/>
 * Description: [Cache related metrics info instances]
 * 
 * @time 2016年8月11日 下午6:20:01
 * @author GUHANJIE
 * @version 1.0.0
 * @since JDK 1.7
 */
public enum CacheMetricsInfo implements MetricsInfo {
	CacheMetrics("Cache related metrics etc."), // record info
    // ============metrics============
    // Cache
	CacheMaxSize("Max size of cache"),	// 最大缓存大小
	CacheMinSize("Min size of cache"),	// 最小缓存大小
	CacheAvgSize("Average size of cache"),	// 平均缓存大小
	CacheCurrentSize("Current size of cache"),	// 当前缓存大小
	CacheHitCount("Number of cache hit"),	// 缓存命中数
	CacheUpdateCount("Number of cache updated"),	//缓存更新次数
	;
	private final String desc;
	
	CacheMetricsInfo(String desc) {
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
