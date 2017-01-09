/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.aop 
 * File Name:			TimeConsumedMetricsAop.java 
 * Create Date:		2016年11月2日 下午4:02:28 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.pinganfu.owl.metrics.MetricsException;
import com.pinganfu.owl.metrics.lib.DefaultMetricsSystem;
import com.pinganfu.owl.metrics.lib.MetricsSourceBuilder;
import com.pinganfu.owl.metrics.lib.MutableMetric;
import com.pinganfu.owl.metrics.lib.MutableRate;

/**
 * Class Name:		TimeConsumedAspect<br/>
 * Description:		[description]
 * Time:					2016年11月2日 下午4:02:28
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
@Aspect
public class TimeConsumedAspect {
	
	private MetricsSourceBuilder msb = new MetricsSourceBuilder(DefaultMetricsSystem.instance(), "test", "time-consumed");
	
	{
		msb.build();
	}
	
	@Around("@annotation(com.pinganfu.owl.metrics.annotation.TimeConsumed)")
	public void timeconsumed(ProceedingJoinPoint pjp) throws Throwable {
		String signature = pjp.getSignature().toShortString();
		long start = System.currentTimeMillis();
		pjp.proceed();
		long timeconsumed = System.currentTimeMillis() - start;
		MutableMetric metric = msb.getMetric(signature);
		MutableRate mr = null;
		if(metric == null) {
			mr = msb.newRate(signature, signature+"time consumed");
		}
		else if(metric instanceof MutableRate) {
			mr = (MutableRate) metric;
		}
		else {
			throw new MetricsException("error to find or create metric for time consumed of method: "+signature);
		}
		mr.add(timeconsumed);
	}
	
}
